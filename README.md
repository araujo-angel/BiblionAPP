# 📚 Biblion APP  

**Equipe:** Ananda Guedes, Angêlica Araújo, Letícia Leite  

---

## 🔎 Definição do Problema  
Atualmente, não existe um aplicativo que una de forma prática e eficiente a experiência de explorar livros em um catálogo organizado com a função de loja (sem processamento de compras).  
Leitores sentem falta de uma solução que permita:  

- Visualizar livros em uma vitrine organizada por categorias;  
- Salvar favoritos e organizar interesses;  
- Criar listas personalizadas simulando um carrinho de compras.  

O **Biblion APP** surge para preencher essa lacuna, combinando a curadoria literária com a funcionalidade de uma loja virtual simplificada.  

---

## 🎯 Objetivo  
Desenvolver um aplicativo mobile em **Kotlin** com **Jetpack Compose** que:  

- Apresente livros em um catálogo interativo e categorizado;  
- Permita favoritar obras e adicioná-las a um carrinho organizacional;  
- Reforce a experiência de descoberta literária com uma interface moderna e intuitiva.  

---

## 📌 Escopo  

### **Incluso**
- Cadastro e login de usuários (via Firebase);  
- Catálogo de livros organizado por categorias;  
- Visualização detalhada de cada livro;  
- Marcar livros como “preferidos”;  
- Adicionar livros ao carrinho;  
- Tela de carrinho acessível pela barra inferior;  
- Navegação fluida entre telas;  
- Verificação de CEP.  

### **Fora do escopo**
- Processamento de pagamento ou compra final (o carrinho é apenas organizacional).  

---

## 👥 Stakeholders  

| Papel          | Descrição                       |  
|----------------|--------------------------------|  
| Usuário Final  | Pessoa que utiliza o aplicativo |  
| Product Owner  | Responsável pela visão do produto |  
| Desenvolvedor  | Implementa e mantém o aplicativo |  

---

## 🛠️ Tecnologias  

- **Linguagem:** Kotlin  
- **UI Framework:** Jetpack Compose  
- **Design:** Material Design  
- **Backend:** Firebase + Firestore  
- **Plataforma:** Android  

---

## ✅ Requisitos Funcionais  

| Código | Requisito |  
|--------|-----------|  
| RF01 | Cadastro com e-mail e senha |  
| RF02 | Login com credenciais |  
| RF03 | Listagem de categorias na tela inicial |  
| RF04 | Exibição de livros por categoria |  
| RF05 | Detalhes de um livro |  
| RF06 | Adição de livros ao carrinho |  
| RF07 | Marcar livro como favorito |  
| RF08 | Tela de carrinho acessível pela barra inferior |  
| RF09 | Persistência de favoritos e carrinho no Firestore |  
| RF10 | UI baseada em Jetpack Compose e Material Design |  

---

## 📅 Roadmap de Desenvolvimento  

### **Fase 1 – Estrutura Inicial**
- Criar estrutura do projeto com Jetpack Compose  
- Configurar Firebase e Firestore  

### **Fase 2 – Autenticação**
- Tela de cadastro (RF01)  
- Tela de login (RF02)  

### **Fase 3 – Home e Catálogo**
- Tela inicial com categorias (RF03)  
- Exibição de livros por categoria (RF04)  
- Integração com Firestore  

### **Fase 4 – Detalhes e Ações**
- Tela de detalhes do livro (RF05)  
- Botão "Adicionar ao carrinho" (RF06)  
- Botão "Favoritar livro" (RF07)  
- Persistência dos dados no Firestore (RF09)  

### **Fase 5 – Carrinho e Navegação**
- Tela do carrinho (RF08)  
- Barra de navegação inferior  
- Integração de navegação entre telas  

### **Fase 6 – Interface e Finalização**
- Aplicar Material Design em todas as telas (RF10)  
- Testes de usabilidade, responsividade e performance  

---

## 🚀 Como Executar o Projeto  

1. Clone este repositório:  
   ```bash
   git clone https://github.com/seu-repositorio/biblion-app.git
2. Abra o projeto no Android Studio.

3. Configure o Firebase com seu próprio projeto.

4. Compile e execute em um dispositivo ou emulador Android.
   
---

## 📖 Conclusão

- O Biblion APP busca transformar a experiência de leitores, oferecendo um espaço de curadoria literária aliado a funcionalidades de organização pessoal, de forma intuitiva e acessível.
