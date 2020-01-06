from lib.PIL import Image as OpenImage,ImageTk
from tkinter import Tk, BooleanVar,Label,Entry,Button,Checkbutton,CENTER,Listbox,END,RIGHT
from hashlib import sha3_256
import os
import Locator

window = Tk()
pathToImage = Locator.main("res\\textures\\Small Logo.png") #Find the small version of the logo
img = ImageTk.PhotoImage(OpenImage.open(pathToImage).resize((256,82),OpenImage.ANTIALIAS)) #Resize the small logo
quitflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\QuitFlag.flg",'w')
quitflag.write("")
quitflag.close()
chosenWorldflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\chosenWorldFlag.flg",'w')
chosenWorldflag.write("")
chosenWorldflag.close()
SeedInfoflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\SeedInfoFlag.flg",'w')
SeedInfoflag.write("")
SeedInfoflag.close()
NewWorldflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\NewWorldflag.flg",'w')
NewWorldflag.write("")
NewWorldflag.close()

def Remove():
    for label in window.grid_slaves():
        label.grid_forget()
    for label in window.pack_slaves():
        label.pack_forget()
    for label in window.place_slaves():
        label.place_forget()

def QuitGame():
    quitflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\QuitFlag.flg",'w')
    quitflag.write("1")
    quitflag.close()
    quit()

def Options():
    window.title("Options")
    Remove()
    Label(window,text="WIP").grid(row=0,column=0)
    Button(window,command=lambda:MainMenu()).grid(row=1,column=0)

def ChooseWorld():
    window.title("Choose World")
    Remove()
    f=[]
    for (dirpath, dirnames, filenames) in os.walk(os.getenv('APPDATA')+"\\Evolution\\saves"):
        f.extend(dirnames)
        break
    WorldList = Listbox(window)
    WorldList.place(relwidth=1,relheight=0.7,relx=0,rely=0.)
    for i in f:
        WorldList.insert(END,i)
    CreateButton = Button(window,text="Create World",command=lambda:CreateWorld()).place(relwidth=0.4,relheight=0.1,relx=0.6,rely=0.7)
    PlayButton = Button(window,text="Load World",command=lambda:play(WorldList.get(WorldList.curselection()))).place(relwidth=0.4,relheight=0.3,relx=0.1,rely=0.7)
    Button(window,text="Return to main menu",command=lambda:MainMenu()).place(relwidth=0.4,relheight=0.1,relx=0.6,rely=0.9)

def CreateWorld():
    window.title("Create World")
    Remove()
    WorldName = Entry(window)
    WorldName.insert(0,"New World")
    WorldName.bind("<FocusIn>", lambda args: WorldName.delete('0', 'end'))
    Seed = Entry(window)
    WorldName.place(relwidth=0.8,relheight=0.3,relx=0.1,rely=0.1)
    PlayButton = Button(window,text="Create World",command=lambda:makeWorld(WorldName.get(),"")).place(relwidth=0.25,relheight=0.2,relx=0.65,rely=0.8)
    BackButton = Button(window,text="Back",command=lambda:ChooseWorld()).place(relwidth=0.25,relheight=0.2,relx=0.1,rely=0.8)
    AdvButton = Button(window,text="Advanced Mode",command=lambda:AdvMode()).place(relwidth=0.5,relheight=0.3,relx=0.25,rely=0.45)

def AdvMode():
    window.title("Create World")
    Remove()
    WorldName = Entry(window)
    WorldName.insert(0,"New World")
    WorldName.bind("<FocusIn>", lambda args: WorldName.delete('0', 'end'))
    Seed = Entry(window)
    Seed.insert(0,"World Seed")
    Seed.bind("<FocusIn>", lambda args: Seed.delete('0', 'end'))
    WorldName.place(relwidth=0.8,relheight=0.3,relx=0.1,rely=0.1)
    Seed.place(relwidth=0.8,relheight=0.3,relx=0.1,rely=0.45)
    NormalMode = Button(window,text="Normal Mode",command=lambda:CreateWorld()).place(relwidth=0.25,relheight=0.2,relx=0.375,rely=0.8)
    PlayButton = Button(window,text="Create World",command=lambda:makeWorld(WorldName.get(),Seed.get())).place(relwidth=0.25,relheight=0.2,relx=0.65,rely=0.8)
    BackButton = Button(window,text="Back",command=lambda:ChooseWorld()).place(relwidth=0.25,relheight=0.2,relx=0.1,rely=0.8)

def makeWorld(world,seed):
    chosenWorldflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\chosenWorldFlag.flg",'w')
    if world != None or world == "New World":
        chosenWorldflag.write(world)
    else:
        chosenWorldflag.write("")
    chosenWorldflag.close()
    quitflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\QuitFlag.flg",'w')
    quitflag.write("0")
    quitflag.close()
    SeedInfoflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\SeedInfoFlag.flg",'w')
    SeedInfoflag.write(seed)
    SeedInfoflag.close()
    NewWorldflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\NewWorldflag.flg",'w')
    NewWorldflag.write("1")
    NewWorldflag.close()
    exit()

def play(world):
    chosenWorldflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\chosenWorldFlag.flg",'w')
    chosenWorldflag.write(world)
    chosenWorldflag.close()
    quitflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\QuitFlag.flg",'w')
    quitflag.write("0")
    quitflag.close()
    SeedInfoflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\SeedInfoFlag.flg",'w')
    SeedInfoflag.write("")
    SeedInfoflag.close()
    NewWorldflag = open(os.getenv('APPDATA')+"\\Evolution\\flags and misc\\NewWorldflag.flg",'w')
    NewWorldflag.write("0")
    NewWorldflag.close()
    exit()
    
def MainMenu():
    Remove()
    window.title("Main Menu")
    play = Button(window,text="Play!",command=lambda:ChooseWorld())
    play.place(relwidth=0.8,relheight=0.1,relx=0.5,rely=0.2,anchor=CENTER)
    options = Button(window,text="Options",command=lambda:Options())
    options.place(relwidth=0.8,relheight=0.1,relx=0.5,rely=0.5,anchor=CENTER)
    quitButton = Button(window,text="Quit Game",command=lambda:QuitGame())
    quitButton.place(relwidth=0.8,relheight=0.1,relx=0.5,rely=0.8,anchor=CENTER)

MainMenu()

window.mainloop()
