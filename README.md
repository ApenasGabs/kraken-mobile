# 📱 Kraken Mobile

> Aplicativo móvel nativo Android (Kotlin + Jetpack Compose) companheiro do **[Kraken (KrakenBridge-Web)](https://github.com/ApenasGabs/KrakenBridge-Web)** para monitoramento em tempo real de agentes autônomos de IA e dashboard ativo **What's Next** (Pull Requests & Issues).

Derivado do módulo Android do Coucou, o **Kraken Mobile** foi reestruturado para eliminar a fricção de permissões/aprovações manuais e atuar como centro de comando móvel e telemetria para o Homelab (`gabisa`).

---

## 🎯 Pilares do Kraken Mobile

1. **Monitoramento Vivo & Telemetria:** Notificação contínua no celular (Foreground Service) que indica em tempo real qual agente está executando, o projeto e o arquivo exato sendo criado ou modificado (`TargetFile`).
2. **Dashboard What's Next:** Visualização integrada de Pull Requests/MRs pendentes de revisão e Issues atribuídas ao desenvolvedor (`@me`), com acionamento do Kraken em 1 toque.
3. **Execução 100% Autônoma:** Remoção de todas as telas bloqueantes de autorização manual e biometria. O Kraken executa tarefas de ponta a ponta sem paralisar o fluxo de desenvolvimento.

---

## 🧭 Estrutura de Navegação (4 Abas)

```
┌────────────────────────────────────────────────────────┐
│  [ 📊 Status ]  [ 📋 What's Next ]  [ 💬 Chat ]  [ ⚙️ Settings ]  │
└────────────────────────────────────────────────────────┘
```

- **📊 Status:** Painel em tempo real de agentes ativos, comandos recentes, arquivos modificados e saúde dos containers do Homelab.
- **📋 What's Next:** Painel consolidado com PRs para revisão e lista de issues do GitHub, com botão *"Resolver com Kraken"*.
- **💬 Chat:** Conversação direta com os agentes do Kraken via smartphone.
- **⚙️ Settings:** Conexão TLS local, pareamento por QR Code e telemetria de rede (LAN / Tailscale).

---

## 🛠️ Build e Execução

Requer JDK 17+ e Android SDK (API 35 / Android 11+).

```bash
# Compilar APK de debug
./gradlew assembleDebug

# Executar testes unitários
./gradlew test
```

O APK compilado é gerado em:
`app/build/outputs/apk/debug/app-debug.apk`

---

## 🔒 Segurança & Privacidade

- **TLS Local:** Conexão ponto a ponto direta entre o smartphone e o nó `gabisa` do Homelab na porta `47821`.
- **Zero Vazamento de Código:** Prompts completos, outputs gigantes de terminal e arquivos integrais **nunca** trafegam pela rede móvel (apenas telemetria, nomes de arquivos e status resumidos).
- **Sem Telemetria Comercial:** 100% livre de rastreadores, anúncios e serviços de nuvem de terceiros.

---

## 📄 Licença

O código do Kraken Mobile é distribuído sob licença [MIT](LICENSE), herdando componentes open-source de Rhytam Biswas e Louis Raillé.
