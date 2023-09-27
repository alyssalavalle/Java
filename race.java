import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Race extends JFrame {
private ArrayList<Racer> racerList; // racers stored in ArrayList
private static Race app;
private final int FIRST_RACER = 50;
private int finishX; // location of finish line, dependent on window width
private boolean raceIsOn = false;
private RacePanel racePanel;

/**
* Constructor instantiates list to track racers sets up GUI
components
*/
  
public Race() {
  super("The Tortoise & The Hare!");
  Container c = getContentPane();
  racePanel = new RacePanel();
  c.add(racePanel, BorderLayout.CENTER);

  racerList = new ArrayList<Racer>();
  setSize(400, 400);
  setVisible(true);
}
  
/**
* prepareToRace method
* uses a dialog box to prompt user for racer types and
* to start the race
* racer types are 't' or 'T' for Tortoise,
* 'h' or 'H' for Hare
* 's' or 'S' will start the race
*/
  
private void prepareToRace() {
  int yPos = FIRST_RACER; // y position of first racer
  final int START_LINE = 40; // x position of start of race
  final int RACER_SPACE = 50; // spacing between racers
  char input;
  
  input = getRacer(); // get input from user


Race.java
while (input != 's' && input != 'S') {

  /* 1. ***** Student write this switch statement
  * input local char variable contains the racer type
  * entered by the user.
  * If input is 'T' or 't',
  * add a Tortoise object to the ArrayList named
    racerList
  * which is an instance variable of this class.
  * The API of the Tortoise constructor is:
  * Tortoise( String ID, int startX, int startY )
  * a sample call to the constructor is
  * new Tortoise( "Tortoise". START_LINE, yPos )
  * where START_LINE is a constant local variable
  * representing the starting x position for the
    race
  * and yPos is a local variable representing the next
  * racer's y position
  *
  * If input is 'H' or 'h',
  * add a Hare object to the ArrayList name racerList.
  * The API of the Tortoise constructor is:
  * Hare( String ID, int startX, int startY )
  * a sample call to the constructor is
  * new Hare( "Hare". START_LINE, yPos )
  * where START_LINE is a constant local variable
  * representing the starting x position for the
    race
  * and yPos is a local variable representing the next
  * racer's y position
  *
  * After adding a racer to the ArrayList racerList,
  * increment yPos by the value of the
  * constant local variable RACER_SPACE
  *
  * If input is anything other than 'T','t','H', or 'h',
  * pop up an error dialog box
  * A sample method call for the output dialog box is :
  * JOptionPane.showMessageDialog( this, "Message" );
  *
  */

  switch (input) {
    case 't':
    case 'T': racerList.add(new Tortoise("Tortoise", START_LINE, yPos));
              yPos = yPos + RACER_SPACE;
              break;
    case 'h':
    case 'H': racerList.add(new Hare("Hare", START_LINE, yPos));
              yPos = yPos + RACER_SPACE;
              break;
    case 'r':
    case 'R': racerList.add(new BlueRectangle("Blue Rectangle", START_LINE, yPos));
              yPos = yPos + RACER_SPACE;
    break;
    default: JOptionPane.showMessageDialog(this, "ERROR");
}
  
    repaint();
    input = getRacer(); // get input from user

  } // end while
} // end prepareToRace

  private class RacePanel extends JPanel {
    /**
    * paint method
    *
    * @param g
    * Graphics context draws the finish line; moves and draws racers
    */

    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // draw the finish line
    finishX = getWidth() - 20;
    g.setColor(Color.blue);
    g.drawLine(finishX, 0, finishX, getHeight());

      if (raceIsOn) {
/* 2. ***** student writes this code
* Loop through instance variable ArrayList racerList, which contains Racer object references, calling move, and then draw for each racer.
* The API for move is:
* void move( )
* The API for draw is:
* void draw( Graphics g )
* where g is the Graphics context passed to
* the paint method
*/
// student code goes here
    for (Racer r : racerList) {
    r.move();
    r.draw(g);
    }
138
139 /** end of student code, part 2 */
140
141 } else // display racers before race begins
142 {
143
144 /* 3. ***** Student writes this code
145 * Loop through instance variable ArrayList
racerList,
146 * which contains Racer object references,
147 * calling draw for each element. (Do not call
move!)
148 * The API for draw is:
149 * void draw( Graphics g )
150 * where g is the Graphics context
151 * passed to this paint method
152 */
153 // student code goes here
154 for (Racer r: racerList)
155 r.draw(g);
156 /** end of student code, part 3 */
157 }
158 }
159 }
160
161 /**
162 * runRace method checks whether any racers have been added to
racerList if
163 * no racers, exits with message otherwise, runs race, calls
repaint to move
164 * & draw racers calls reportRaceResults to identify winners(s)
calls reset
Page 4
Race.java
165 * to set up for next race
166 * @throws InterruptedException
167 */
168 public void runRace() throws InterruptedException {
169 if (racerList.size() == 0) {
170 JOptionPane.showMessageDialog(this, "The race has no
racers. exiting",
171 "No Racers", JOptionPane.ERROR_MESSAGE);
172 System.exit(0);
173 }
174 raceIsOn = true;
175 while (!findWinner()) {
176 // slow down here if you know how
177 Thread.sleep(30);
178 repaint();
179 } // end while
180
181 reportRaceResults();
182 reset();
183 }
184
185 /**
186 * gets racer selection from user
187 *
188 * @return first character of user entry if user presses
cancel, exits the
189 * program
190 */
191 private char getRacer() {
192 String input = JOptionPane.showInputDialog(this, "Enter a
racer:"
193 + "\nt for Tortoise, h for hare, r for rectangle" +
"\nor s to start the race");
194 if (input == null) {
195 System.out.println("Exiting");
196 System.exit(0);
197 }
198 if (input.length() == 0)
199 return 'n';
200 else
201 return input.charAt(0);
202 }
203
204 /**
205 * findWinners: checks for any racer whose x position is past
the finish line
Page 5
Race.java
205 * findWinners: checks for any racer whose x position is past
the finish line
206 *
207 * @return true if any racer's x position is past the finish
line or false if
208 * no racer's x position is past the finish line
209 */
210 private boolean findWinner() {
211 for (Racer r : racerList) {
212 if (r.getX() > finishX) {
213 r.isWinner = true;
214 return true;
215 }
216 }
217
218
219 return false;
220 }
221 /**
222 * reportRaceResults : compiles winner names and prints message
winners are
223 * all racers whose x position is past the finish line
224 */
225 private void reportRaceResults() {
226 raceIsOn = false;
227 String results = "Racer ";
228 for (int i = 0; i < racerList.size(); i++) {
229 if (racerList.get(i).getX() > finishX) {
230 results += (i + 1) + ", a " +
racerList.get(i).getID() + ", ";
231 }
232 }
233
234 JOptionPane.showMessageDialog(this, results + " win(s) the
race ");
235
236 }
237
238 /**
239 * reset: sets up for next race: sets raceIsOn flag to false
clears the list
240 * of racers resets racer position to FIRST_RACER enables
checkboxes and
241 * radio buttons
242 * @throws InterruptedException
243 */
Page 6
Race.java
244 private void reset() throws InterruptedException {
245 char answer;
246 String input = JOptionPane.showInputDialog(this, "Another
race? (y, n)");
247 if (input == null || input.length() == 0) {
248 System.out.println("Exiting");
249 System.exit(0);
250 }
251
252 answer = input.charAt(0);
253 if (answer == 'y' || answer == 'Y') {
254 raceIsOn = false;
255 racerList.clear();
256 app.prepareToRace();
257 app.runRace();
258 } else
259 System.exit(0);
260 }
261
262 /**
263 * main instantiates the Race object app
264 * calls runRace method
265 * @throws InterruptedException
266 */
267 public static void main(String[] args) throws
InterruptedException {
268 app = new Race();
269 app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
270 app.prepareToRace();
271 app.runRace();
272 }
273
274 }
Page 7
