### Objetivo do Projeto
   O objetivo deste projeto é simular um jogo de invasão multi-threaded, onde vários jogadores (threads) competem para dominar diferentes países. O projeto utiliza conceitos fundamentais de sistemas operacionais, como mutexes e semáforos, para coordenar as ações das threads e evitar condições de corrida.

## Descrição Geral
   O jogo envolve múltiplos jogadores, cada um representado por uma thread, que tentam invadir e dominar diferentes países. O jogo é executado em rodadas, onde cada jogador tenta conquistar ou atacar um país. O projeto simula um ambiente concorrente onde as threads precisam coordenar suas ações, esperando que todas as outras threads estejam prontas antes de prosseguir para a próxima rodada.

## Componentes do Projeto
GameMain: Classe principal que gerencia a inicialização do jogo e a criação das threads dos jogadores. Também gerencia a lista de países e controla o número de rodadas do jogo.

Player: Cada instância dessa classe representa um jogador no jogo. Os jogadores tentam dominar países e podem realizar ataques com um dano aleatório. A classe estende Thread, permitindo que cada jogador opere em paralelo com os outros.

Country: Representa um país no jogo, com atributos para nome, domínio (o jogador que controla o país) e vida (resistência a ataques). A classe inclui métodos para definir o domínio e para processar invasões.

Verify: Classe responsável por verificar o estado das threads dos jogadores. Implementa uma lógica de barreira que garante que todos os jogadores estejam prontos antes de iniciar a próxima rodada.

SemaphoreCustom: Um semáforo personalizado que controla o número de threads que podem acessar uma seção crítica simultaneamente. Ele é utilizado para gerenciar a entrada e saída de threads em seções críticas durante o jogo.

## Funcionamento do Jogo
   Inicialização:

O jogo começa com a criação de um número especificado de jogadores (threads).
Cada jogador é atribuído a um país e tenta dominá-lo.
Rodadas:

O jogo é dividido em várias rodadas.
Em cada rodada, os jogadores tentam atacar outros países ou fortalecer seu domínio sobre um país que já controlam.
As threads aguardam umas pelas outras ao final de cada rodada, utilizando o objeto lock para sincronização.
Coordenação das Threads:

A classe Verify monitora o estado das threads, garantindo que todas estejam prontas antes de prosseguir para a próxima rodada.
Um semáforo personalizado (SemaphoreCustom) é utilizado para controlar o acesso das threads a determinadas operações críticas, como invasões e ataques.
Encerramento:

O jogo continua até que o número de rodadas especificado chegue a zero.
Ao final, um relatório de dominância é gerado, exibindo o estado de cada país, incluindo o nome do jogador que o controla e a vida restante.
## Conceitos de Sistemas Operacionais Aplicados
   Threads: O projeto utiliza threads para simular a concorrência entre jogadores. Cada jogador é representado por uma thread que opera independentemente, tentando atingir seus objetivos no jogo.

Mutex (Mutual Exclusion): A sincronização entre as threads é realizada utilizando um objeto lock. Isso garante que as threads esperem umas pelas outras ao final de cada rodada, prevenindo condições de corrida.

Semáforos: O semáforo personalizado (SemaphoreCustom) limita o número de threads que podem acessar simultaneamente uma seção crítica, como a realização de um ataque. Isso evita que múltiplas threads realizem operações críticas ao mesmo tempo, o que poderia levar a inconsistências.

Wait e Notify: As operações wait() e notify() são usadas para coordenar as threads, garantindo que todas estejam prontas antes de avançar no jogo.

