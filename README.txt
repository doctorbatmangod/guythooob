This is a README. The great GitHub was angered by its absence, so I created a README.
So yeah.
This project is supposed to analyze the stock market and make us tons of monies.
My (Daniel Chiquito's) job is to write the code that does the work.
This is where I keep the code, for all to gaze at and tremble at my excellence.

I'm using eclipse. If anyone else wants to edit code, I would recommend opening eclipse with the workspace as the repo folder (stock-analyzer).
That's where I put the project.
Hopefully, if you open it there, it should set itself up.

As of 11-16-2012, I have written the basic DataPoint interface to represent stock data on a particular day.
Yahoo does not offer finer data than daily, so for now we will make do.
I have also created the PointSequence to represent a sequence of DataPoints.
It simply extends ArrayList<DataPoint>, so you can use it as an ordinary list.
It includes some basic statistical methods, with more to follow as I learn what they are.

Also, there is APIScraper. It reads the data from Yahoo and puts it into DataPoints and PointSequences.

That's all for now, folks!