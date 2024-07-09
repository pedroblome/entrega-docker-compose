# Usar uma imagem do Node como base
FROM node:14

# Definir diretório de trabalho
WORKDIR /usr/src/app

# Copiar os arquivos package.json e package-lock.json
COPY package*.json ./

# Instalar dependências do projeto
RUN npm install

# Copiar todos os outros arquivos do projeto
COPY . .

# Construir a aplicação para produção
RUN npm run build

# Instalar um servidor HTTP simples para servir conteúdo estático
RUN npm install -g serve

# Comando para rodar a aplicação
CMD ["serve", "-s", "build", "-l", "3000"]
