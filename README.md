# gassales
Gas Sales Management


1 - Instalar docker (ver instrucoes no docker hub)
	https://docs.docker.com/engine/install/ubuntu/
	https://docs.docker.com/engine/install/linux-postinstall/
	https://docs.docker.com/compose/install/
	https://docs.docker.com/compose/completion/
2 - baixar imagem do mysql
	docker pull mysql:latest
3 - instalar mysql client
	sudo apt install mysql-client-core-8.0 
4 - Iniciar o container do mysql por forma a aceitar conexoes a partir do host
	docker run --name gassalesdb -e MYSQL_ROOT_PASSWORD=gassales -d -p 127.0.0.1:3306:3306 mysql:latest
5 - Aceder ao container e criar o user da app
	mysql -u root -p -h 127.0.0.1 -P3306
	CREATE USER 'gassales'@'172.17.0.1' IDENTIFIED BY 'gassales';
	GRANT ALL PRIVILEGES ON gassales.* TO 'gassales'@'172.17.0.1';
	FLUSH PRIVILEGES;
6 - Sair e testar com o user criado
	exit;
	mysql -u gassales -p -h 127.0.0.1 -P3306
