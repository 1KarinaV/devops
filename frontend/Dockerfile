# Builder stage: собираем фронтенд
FROM --platform=linux/arm64 node:21.7.1 as builder
WORKDIR /app
COPY . ./
RUN npm config set legacy-peer-deps true &&  \
    npm install --save-dev ajv@^7 && \
    npm install && \
    npm run build

FROM --platform=linux/arm64 arm64v8/nginx
COPY --from=builder /app/build /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]