# Stage 1: build
FROM node:18-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .

# URL de la API que verá el navegador (host)
ARG REACT_APP_API_URL=http://localhost:8083
ENV REACT_APP_API_URL=$REACT_APP_API_URL

RUN npm run build

# Stage 2: servir estáticos
FROM nginx:alpine

COPY --from=builder /app/build /usr/share/nginx/html

# SPA: todas las rutas llevan a index.html (React Router)
RUN echo 'server { listen 80; location / { root /usr/share/nginx/html; index index.html; try_files $uri $uri/ /index.html; } }' > /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]