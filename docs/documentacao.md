# Documentação Técnica do Projeto — Memória de Elefante

Este documento detalha a arquitetura, as decisões de design e a modelação orientada a objetos do nosso projeto, um jogo de 
memória dinâmico desenvolvido em Java com interface gráfica em JavaFX.


# 1. Arquitetura Geral do Sistema

O projeto foi desenhado para assegurar uma separação total entre a lógica de negócio (as regras do jogo) e a interface gráfica 
do utilizador.

1.1 - Modelo: Contém as entidades do domínio ("Jogo", "Tabuleiro", "Carta", "Jogador", "Nivel"). 
Esta camada é completamente agnóstica em relação ao JavaFX, o que facilita a escalabilidade, a reutilização de código e a 
execução determinística de testes unitários.
1.2 - Interface: Camada implementada em JavaFX que escuta as interações do utilizador (cliques nos botões do ecrã) e traduz 
essas ações em chamadas aos métodos do Modelo, atualizando a interface gráfica com base no estado do jogo.


# 2. Modelação do Domínio e Conceitos POO Aplicados

A robustez e a escalabilidade do código baseiam-se na aplicação rigorosa dos pilares fundamentais da Programação Orientada 
a Objetos:

2.1 - Abstração: A abstração consiste em focar apenas nas características essenciais de um objeto, ignorando os detalhes 
complexos de como ele funciona por trás dos panos. É criar um "molde" conceptual.
Exemplo no projeto: A classe Carta. Ela é uma classe abstrata. O motor do jogo não precisa de saber que linhas de código 
correm quando uma carta é ativada. Ele apenas precisa de saber que "todas as cartas podem ser viradas, emparelhadas e ativadas". 
A classe Carta declara o método ativar(Jogo jogo) mas não o implementa, delegando essa complexidade para quem herdar dela.

2.2 - Encapsulamento: É a proteção dos dados internos de um objeto. O encapsulamento agrupa os dados (atributos) e os métodos que 
os manipulam na mesma classe, escondendo o estado interno do mundo exterior (usando private) e permitindo a alteração apenas através 
de regras rigorosas (usando métodos públicos).
Exemplo no projeto: O estado de uma Carta e a sua pontuação do Jogador são estritamente privados. Não é possível alterar estes 
valores diretamente de fora da classe. O estado da carta só muda através dos métodos virar() e emparelhar(), e as tentativas do 
jogador só diminuem através do método consumirTentativa()

2.3 - Herança: É o mecanismo que permite criar novas classes (subclasses) baseadas em classes já existentes (superclasses). 
A subclasse herda os atributos e comportamentos da classe mãe, promovendo a reutilização de código e criando uma hierarquia clara.
Exemplo no projeto: As classes CartaNormal, CartaRevelar, CartaTentativaExtra, etc..., herdam diretamente da classe Carta. 
Isto significa que não tiveste de escrever o código de virar(), getSimbolo() ou o atributo id de novo para cada uma delas. 
Elas já nascem com tudo o que uma Carta tem, precisando apenas de especificar o que as torna únicas.

2.4 - Polimorfismo: É a capacidade de objetos de classes diferentes (mas que partilham a mesma classe mãe) responderem à mesma 
chamada de método, cada um à sua maneira. Exemplo no projeto: O método carta.ativar(this) chamado dentro do Jogo.java.
O Jogo tem uma lista genérica do tipo Carta. Quando um par é formado, o motor do jogo simplesmente invoca o método carta.ativar(this).
O Jogo não faz a menor ideia se a carta é Normal ou Especial. Se, em tempo de execução, a carta for uma CartaNormal, 
o polimorfismo garante que o código executado apenas dá 10 pontos. Se a carta for uma CartaTentativaExtra (por exemplo), o Java 
automaticamente sabe que tem de ir correr o código que dá as tentativas extra ao jogador. 

2.5 - Tipos Enumerados: Para evitar inconsistências de estado comuns no uso de strings ou inteiros soltos, recorreu-se ao uso de um 
enumerado (Enum) para mapear o ciclo de vida das cartas: EstadoCarta (VIRADA_BAIXO, VIRADA_CIMA, EMPARELHADA). Isto restringe os estados 
possíveis a um conjunto fechado e seguro em tempo de compilação.

# 3. Relações entre Entidades (Associação e Composição)

Para além dos pilares da POO, a arquitetura do domínio sustenta-se em relações sólidas entre os objetos:

3.1 - Composição (1 para N): A classe Tabuleiro é composta por uma lista de objetos do tipo Carta. Se o tabuleiro for destruído 
(por exemplo, ao avançar de nível), as cartas que o compõem também deixam de existir, garantindo uma gestão de memória eficiente.

3.2 - Associação: A classe Jogo centraliza as operações associando-se ao Tabuleiro e ao Jogador. O motor orquestra a comunicação entre 
ambos sem assumir a posse exclusiva dos seus ciclos de vida.


# 4. Gestão e Tratamento de Erros (Exceções)

A robustez da aplicação contra ações inválidas do utilizador na interface gráfica é garantida pelo "disparo" de exceções 
personalizadas: JogoException
Esta exceção acontece quando o utilizador tenta selecionar uma carta que já se encontra virada para cima ou já emparelhada, 
ou quando o tabuleiro se encontra temporariamente bloqueado aguardando o fecho visual de um par incorreto.
Este mecanismo impede que o estado interno do jogo entre em colapso devido a interações rápidas ou cliques acidentais no ecrã.


# 5. Estratégia de Testes Unitários

A qualidade do domínio é validada através de uma classe de testes automatizados com JUnit 5. Os testes evitam o comportamento 
aleatório do baralhamento simulando cenários e instâncias de cartas controladas (pelo programador).
Os casos de teste dividem-se em três vertentes essenciais:

5.1 - Casos Normais: Validação do estado inicial do Nível 1 e garantia de que a seleção da primeira carta de um turno não deduz 
tentativas desnecessariamente.

5.2 - Casos Limite e Erros: Verificação do correto disparo da exceção JogoException ao selecionar inputs inválidos e teste do 
estado bloqueado do "motor" de jogo após um par falhado.

5.3 - Casos Polimórficos: Testes focados em garantir que o método ativar() produz efeitos perfeitamente distintos no Jogador dependendo 
da subclasse real testada (ex: acréscimo de jogadas com CartaTentativaExtra versus pontuação simples com CartaNormal), comprovando o 
comportamento dinâmico do sistema.