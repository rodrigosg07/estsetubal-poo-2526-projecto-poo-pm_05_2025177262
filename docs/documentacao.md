# Documentação Técnica do Projeto — Memória de Elefante
Este documento detalha a arquitetura, as decisões de design e a modelação orientada a objetos do nosso projeto,
um jogo de memória dinâmico desenvolvido em Java com interface gráfica em JavaFX.

# 1. Arquitetura Geral do Sistema
O projeto foi desenhado para assegurar uma separação total entre a lógica de negócio (as regras do jogo) e a 
interface gráfica do utilizador.

1.1 - Modelo: Contém as entidades do domínio (`Jogo`, `Tabuleiro`, `Carta`, `Jogador`, `Nivel`). 
Esta camada é completamente agnóstica em relação ao JavaFX, o que facilita a escalabilidade, a reutilização de 
código e a execução determinística de testes unitários.
1.2 - Interface: Camada implementada em JavaFX que escuta as interações do utilizador (cliques nos botões do ecrã) 
e traduz essas ações em chamadas aos métodos do Modelo, atualizando a interface gráfica com base no estado do jogo.

## 2. Modelação do Domínio e Conceitos POO Aplicados
A robustez e a escalabilidade do código baseiam-se na aplicação rigorosa dos quatro pilares fundamentais da 
Programação Orientada a Objetos:

2.1 - Abstração: A abstração consiste em focar apenas nas características essenciais de um objeto, ignorando os 
detalhes complexos de como ele funciona por trás dos panos. É criar um "molde" conceptual.
Exemplo no projeto: A classe Carta. Ela é uma classe abstrata. O motor do jogo não precisa de saber que linhas de código 
correm quando uma carta é ativada. Ele apenas precisa de saber que "todas as cartas podem ser viradas, emparelhadas e ativadas". 
A classe Carta declara o método ativar(Jogo jogo) mas não o implementa, delegando essa complexidade para quem herdar dela.  
2.2 - É a proteção dos dados internos de um objeto. O encapsulamento agrupa os dados (atributos) e os métodos que os manipulam 
na mesma classe, escondendo o estado interno do mundo exterior (usando private) e permitindo a alteração apenas através de 
regras rigorosas (usando métodos públicos).
2.3 - Herança: É o mecanismo que permite criar novas classes (subclasses) baseadas em classes já existentes (superclasses). A subclasse herda os atributos e comportamentos da classe mãe, promovendo a reutilização de código e criando uma hierarquia clara.


Exemplo no teu projeto: As tuas classes CartaNormal, CartaRevelar, CartaTentativaExtra, etc., herdam diretamente da classe Carta. Isto significa que não tiveste de escrever o código de virar(), getSimbolo() ou o atributo id de novo para cada uma delas. Elas já nascem com tudo o que uma Carta tem, precisando apenas de especificar o que as torna únicas.