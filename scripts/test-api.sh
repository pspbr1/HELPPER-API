#!/usr/bin/env bash
set -euo pipefail

API_BASE_URL="${API_BASE_URL:-http://localhost:8080/api}"
EMAIL="${EMAIL:-admin@teste.com}"
SENHA="${SENHA:-123456}"
CPF="${CPF:-12345678900}"
NOME="${NOME:-Usuário Teste}"

echo "==> Base URL: ${API_BASE_URL}"

echo "==> Registrando usuário (pode falhar se já existir)..."
curl -sS -X POST "${API_BASE_URL}/auth/registrar" \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"${EMAIL}\",\"senha\":\"${SENHA}\",\"cpf\":\"${CPF}\",\"nome\":\"${NOME}\"}" \
  || true
echo ""

echo "==> Fazendo login para obter token..."
TOKEN="$(
  curl -sS -X POST "${API_BASE_URL}/auth/login" \
    -H "Content-Type: application/json" \
    -d "{\"email\":\"${EMAIL}\",\"senha\":\"${SENHA}\"}" \
    | python - <<'PY'
import json, sys
data = json.load(sys.stdin)
print(data.get("token", ""))
PY
)"

if [[ -z "${TOKEN}" ]]; then
  echo "Falha ao obter token. Verifique credenciais/endpoint."
  exit 1
fi

AUTH_HEADER="Authorization: Bearer ${TOKEN}"

echo "==> Criando Prestador..."
PRESTADOR_ID="$(
  curl -sS -X POST "${API_BASE_URL}/prestador" \
    -H "Content-Type: application/json" \
    -H "${AUTH_HEADER}" \
    -d "{\"email\":\"${EMAIL}\",\"senha\":\"${SENHA}\",\"cpf\":\"${CPF}\",\"nome\":\"${NOME}\"}" \
    | python - <<'PY'
import json, sys
data = json.load(sys.stdin)
print(data.get("id", ""))
PY
)"
echo "Prestador ID: ${PRESTADOR_ID}"

if [[ -n "${PRESTADOR_ID}" ]]; then
  echo "==> Listando Prestadores..."
  curl -sS -X GET "${API_BASE_URL}/prestador" -H "${AUTH_HEADER}"
  echo ""

  echo "==> Buscando Prestador por ID..."
  curl -sS -X GET "${API_BASE_URL}/prestador/${PRESTADOR_ID}" -H "${AUTH_HEADER}"
  echo ""

  echo "==> Atualizando Prestador..."
  curl -sS -X PUT "${API_BASE_URL}/prestador/${PRESTADOR_ID}" \
    -H "Content-Type: application/json" \
    -H "${AUTH_HEADER}" \
    -d "{\"cidade\":\"São Paulo\"}"
  echo ""
fi

echo "==> Criando Contratante..."
CONTRATANTE_ID="$(
  curl -sS -X POST "${API_BASE_URL}/contratantes" \
    -H "Content-Type: application/json" \
    -H "${AUTH_HEADER}" \
    -d "{\"email\":\"${EMAIL}\",\"senha\":\"${SENHA}\",\"cpf\":\"${CPF}\",\"nome\":\"${NOME}\",\"endereco\":\"Rua A\"}" \
    | python - <<'PY'
import json, sys
data = json.load(sys.stdin)
print(data.get("id", ""))
PY
)"
echo "Contratante ID: ${CONTRATANTE_ID}"

if [[ -n "${CONTRATANTE_ID}" ]]; then
  echo "==> Listando Contratantes..."
  curl -sS -X GET "${API_BASE_URL}/contratantes" -H "${AUTH_HEADER}"
  echo ""

  echo "==> Buscando Contratante por ID..."
  curl -sS -X GET "${API_BASE_URL}/contratantes/${CONTRATANTE_ID}" -H "${AUTH_HEADER}"
  echo ""

  echo "==> Atualizando Contratante..."
  curl -sS -X PUT "${API_BASE_URL}/contratantes/${CONTRATANTE_ID}" \
    -H "Content-Type: application/json" \
    -H "${AUTH_HEADER}" \
    -d "{\"endereco\":\"Rua B\"}"
  echo ""
fi

echo "==> Criando Serviço..."
SERVICO_ID="$(
  curl -sS -X POST "${API_BASE_URL}/servico/create" \
    -H "Content-Type: application/json" \
    -H "${AUTH_HEADER}" \
    -d "{\"tipo\":\"Pintura\",\"descricao\":\"Pintar sala\",\"orcamento\":\"100\"}" \
    | python - <<'PY'
import json, sys
data = json.load(sys.stdin)
print(data.get("id", ""))
PY
)"
echo "Serviço ID: ${SERVICO_ID}"

if [[ -n "${SERVICO_ID}" ]]; then
  echo "==> Atualizando status do Serviço..."
  curl -sS -X PATCH "${API_BASE_URL}/servico/${SERVICO_ID}/status" \
    -H "Content-Type: application/json" \
    -H "${AUTH_HEADER}" \
    -d "{\"status\":\"ABERTO\"}"
  echo ""

  if [[ -n "${PRESTADOR_ID}" ]]; then
    echo "==> Vinculando Prestador ao Serviço..."
    curl -sS -X POST "${API_BASE_URL}/servico/${SERVICO_ID}/profissionais/${PRESTADOR_ID}" \
      -H "${AUTH_HEADER}"
    echo ""
  fi
fi

if [[ -n "${PRESTADOR_ID}" ]]; then
  echo "==> Deletando Prestador..."
  curl -sS -X DELETE "${API_BASE_URL}/prestador/${PRESTADOR_ID}" -H "${AUTH_HEADER}"
  echo ""
fi

if [[ -n "${CONTRATANTE_ID}" ]]; then
  echo "==> Deletando Contratante..."
  curl -sS -X DELETE "${API_BASE_URL}/contratantes/${CONTRATANTE_ID}" -H "${AUTH_HEADER}"
  echo ""
fi

if [[ -n "${SERVICO_ID}" ]]; then
  echo "==> Deletando Serviço..."
  curl -sS -X DELETE "${API_BASE_URL}/servico/${SERVICO_ID}/servico" -H "${AUTH_HEADER}"
  echo ""
fi

echo "==> Testes concluídos."
