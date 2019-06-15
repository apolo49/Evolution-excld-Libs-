from tkinter import Tk, BooleanVar,Label,Entry,Button,Checkbutton,CENTER
from pickle import dump,HIGHEST_PROTOCOL,load
import Locator

def main():
    def save_obj(obj, name): #to save an object and data type
        file=open(Locator.main("src\\python\\obj\\"+name+".txt"),"w")
        file.write(str(obj))
        file.close()

    def resume():
        save_obj(0,"Quit_type")
        exit()
    def options():
        for label in window.grid_slaves(): #go through all elements in the window
            label.grid_forget() #forget the element
        Label(window,text="WIP Please refer back later").grid(row=5,column=1)
    def quit_game():
        save_obj(1,"Quit_type")
        exit()

    window = Tk() #Create window
    window.title("pause")
    Label(window,text="Game Paused").grid(row=0,column=0)
    Resume = Button(window,text="Resume",command=lambda:resume(),width=10)
    Resume.grid(row=1,column=0)
    Quit = Button(window,text="Exit game",command=lambda:quit_game(),width=10)
    Quit.grid(row=3,column=0)
    Options = Button(window,text="Options",command=lambda:options(),width=10)
    Options.grid(row=2,column=0)
    
    window.mainloop() #Window loop
    
main()
