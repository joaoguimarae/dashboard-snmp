# Dashboard SNMP

Projeto da disciplina de Gerência de Redes para ler informações da máquina (MIB-2) via protocolo SNMP.

## Configuração e Execução

Para o código conseguir puxar os dados, é necessário configurar o serviço SNMP nativo do Windows:

1. Aperte Windows + R, digite services.msc e dê Enter.
2. Encontre o *Serviço SNMP* na lista, clique com o botão direito e vá em *Propriedades*.
3. Na aba *Segurança*:
    Adicione a comunidade public com a permissão de *Somente Leitura*.
    Marque a opção *Aceitar pacotes SNMP de qualquer host*.
4. Na aba *Agente*:
    Preencha os campos *Contato* (ex: seu nome) e *Localização* (ex: seu laboratório). Se deixar em branco, o painel não vai exibir essas informações.
    Marque todas as caixas de serviços na parte de baixo (Físico, Camada de enlace, Internet, etc).
5. Clique em Aplicar, dê OK e reinicie o serviço.

Depois de fazer essa configuração, basta iniciar o projeto pela classe principal (SnmpApplication.java) no IntelliJ e abrir o navegador no endereço: http://localhost:8080
