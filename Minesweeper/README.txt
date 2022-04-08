=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: jamgott
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. I used a 2-D array to model the game state. It keeps track of and provides
  easy and instant access to each cell object. The 2-D array is important
  to the model because it allows for easy and intuitive
  transfer between the jframe coordinates
  and its own row column coordinates. It also corresponds well with
  Minesweeper in general as Minesweeper's board is a 2d grid.
  This makes the state generally more intuitive and easy to 
  conceptualize. For all of these reasons, it is an appropriate
  use of the concept. The feedback I received in my project
  proposal also seemed to agree with this.

  2. I used recursion for my flipCell function in GameCourt.java
  which dealt with flipping cells adjacent to the one that had
  been clicked on if neither contained a mine and the one clicked
  on had no adjacent mines. This is appropriate because it works
  more efficiently than any equivalent method using loops. The feedback
  I received in my project proposal was that this idea was good to
  go.

  3. I used junit testing to make sure my Cell functions and my applicable
  GameCourt functions were working correctly. This helped in dealing
  with corner and edge cases as well as in finding where various 
  errors and exceptions were occurring when actually running my game.
  This is exactly what testing is meant for and is therefore an
  appropriate use of it.
  The feedback I received in my project proposal was that this ideas
  was good to go.

  4. I used IO to implement a save and load feature for my game.
  When saving, the game calls a function that writes important
  information to a txt file. The load function can then read back
  that txt file and reverse engineer the process of the save
  function, thus restoring the game to its original state. This
  is exactly the kind of of thing IO is meant for so it is an
  appropriate use of the concept. This was not a part of my original
  proposal but the TA I emailed after that said this should work
  well.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  The Cell class holds the information for each square on the mine field
  game board. It is used to hold this information and also provides 
  functions to retrieve and adjust it. Finally, it holds a draw function
  that draws each cell of the board.
  
  The GameCourt class holds the state of the game and uses state variables
  in conjunction with mouse listeners and the cells to appropriately change
  the game state. It uses these interactions to properly monitor and respond
  to the users actions.
  
  The Game class was mostly unadjusted from the starter code and simply
  contains the instantiation of the game court as well as the jframe, 
  appropriate jbuttons, and the calls needed to start the game.
  
  The GameObj class was also mostly unadjusted from the starter code
  and extended my cell class. I also found several of the getters useful
  in implementing some of the functions.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  No.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  There is a decent separation of functionality although state variables are
  mixed into individual functions and some important conditions to the game
  are only in the mouse listener functions. Some functions are additionally
  quite long but I chose not to split them up into multiple functions for
  ease of properly keeping track of state variables and local variables.
  Given the chance to refactor, I would look for better ways to separate
  functionality so my tests had more coverage, and I would look to shorten
  unnecessary or repeated code.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
