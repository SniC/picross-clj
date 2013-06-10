Picross
=========


Gamerules:

Picross is a picture logic puzzle in which cells in a grid must be colored or left blank according to numbers at the side of the grid to reveal a hidden picture. 
In this puzzle type, the numbers are a form of discrete tomography that measures how many unbroken lines of filled-in squares there are in any given row or column. 

For example, a clue of "5 2 1" would mean there are sets of five, two, and one filled squares, in that order, with at least one blank square between successive groups.











A simple server based game built with
[Ring](https://github.com/ring-clojure),
[Compojure](https://github.com/weavejester/compojure),
[lib-noir](https://github.com/noir-clojure/lib-noir) and
[Hiccup](https://github.com/weavejester/hiccup). The project template
was borrowed from [Luminus](http://www.luminusweb.net/). 

# Download and run: 

    $ git clone git@github.com:SniC/picross-clj.git
    $ cd picross-clj
    $ lein ring server
    
A browser window will open and you'll be able to play.

# Run tests:    

    $ lein test

# Deploy to Heroku:

    $ heroku create --stack cedar
    $ git push heroku master    

More, see [Heroku](https://blog.heroku.com/archives/2011/7/5/clojure_on_heroku).
