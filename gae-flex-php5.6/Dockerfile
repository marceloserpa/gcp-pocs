FROM php:5.6.38-apache

ENV PORT 8080

COPY config/ports.conf /etc/apache2/ports.conf
COPY config/000-default.conf /etc/apache2/sites-enabled/000-default.conf

EXPOSE 8080

COPY src/ /var/www/html/

RUN printenv
