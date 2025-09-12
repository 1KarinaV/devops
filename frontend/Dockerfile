FROM node:21.7.1 as builder
WORKDIR /app
COPY . ./
RUN npm config set legacy-peer-deps true &&  \
    npm install --save-dev ajv@^7 && \
    npm install && \
    npm run build

# Use Nginx as the production server
FROM arm64v8/nginx
# FROM nginx
COPY --from=builder /app/build /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]