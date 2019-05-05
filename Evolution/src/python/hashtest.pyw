from hashlib import sha3_256
from pickle import dump,HIGHEST_PROTOCOL,load
from urllib.request import urlopen
from os.path import abspath
from PIL import Image as OpenImage
from PIL import ImageTk
from tkinter import *


#General Setup
attempts = 0
abspath(__file__).strip("src\\pyhton\\hash test.pyw")+"on\\res\\textures\\stone.png"
LoggedInFile = open(abspath(__file__).strip("src\\pyhton\\hash test.pyw")+"on\\src\\python\\obj\\LoggedIn.flg",'w')
LoggedInFile.write("false")
LoggedInFile.close()

def save_obj(obj, name):
    with open(abspath(__file__).strip("src\\pyhton\\hash test.pyw")+"on\\src\\python\\obj\\"+ name + '.pkl', 'wb') as f:
        dump(obj, f, HIGHEST_PROTOCOL)
        f.close()

def load_obj(name):
    try:
        with open(abspath(__file__).strip("src\\pyhton\\hash test.pyw")+"on\\src\\python\\obj\\" + name + '.pkl', 'rb') as f:
            return load(f)
            f.close()
    except:
        save_obj({},name)
        load_obj(name)

#End general setup


#setup window
window = Tk()
window.title("Login")
pathToImage = abspath(__file__).strip("src\\pyhton\\hash test.pyw")+"on\\res\\textures\\Small Logo.png"
pathToBack = abspath(__file__).strip("src\\pyhton\\hash test.pyw")+"on\\res\\textures\\stone.png"
img = ImageTk.PhotoImage(OpenImage.open(pathToImage).resize((256,82),OpenImage.ANTIALIAS))
back = ImageTk.PhotoImage(OpenImage.open(pathToBack).resize((512,400),OpenImage.ANTIALIAS))
check = BooleanVar()

try:
    dataFile = urlopen("https://www.dropbox.com/s/18mqoxgg85m20at/logs.txt?dl=1")
    data = dataFile.read()
    dataFile.close()
except:
    data="Please connect to the internet"
    
#Labels
Label(window,image=img).grid(row=0,column=0,columnspan=2)
Label(window,image=back,text=data, compound=CENTER,fg="white").grid(row=1,column=0,columnspan=2)
Label(window,text="Username: ").grid(row=2,column=0)
Label(window,text="Password: ").grid(row=3,column=0)

#Entry boxes
UsernameBox=Entry(window,width=40)
UsernameBox.grid(row=2,column=1)
PasswordBox=Entry(window,width=40)
PasswordBox.grid(row=3,column=1)

#Default buttons
loginButton=Button(window,text="Login",command=lambda:login(UsernameBox.get(),PasswordBox.get(),check.get())).grid(row=4,column=0)
createButton=Button(window,text="Create Account",command=lambda:create(UsernameBox.get(),PasswordBox.get())).grid(row=4,column=1)
remember = Checkbutton(window,text="Remember me",variable=check)
remember.grid(row=7,column=0)

#end window setup


def create(userN,Pass):
    dic = load_obj('dic')
    hashres=sha3_256((userN+Pass).encode('utf-8')).hexdigest()
    if int(hashres,16) not in dic:
        dic[int(hashres,16)] = {userN:Pass}
        for label in window.grid_slaves():
            if int(label.grid_info()["row"]) == 5:
                label.grid_forget()
                label=Label(window,text="Please now login").grid(row=5,column=1)
            else:
                Label(window,text="Please now login").grid(row=5,column=1)
    else:
        if str({userN:Pass}==dic[int(hashres,16)]):
            for label in window.grid_slaves():
                if int(label.grid_info()["row"]) == 5:
                    label.grid_forget()
                    Label(window,text="User already exists").grid(row=5,column=1)
                else:
                    Label(window,text="User already exists").grid(row=5,column=1)
        else:
            dic[int(hashres,16)+1] = {userN:Pass}
    if dic != load_obj('dic'):    
        save_obj(dic,'dic')
        for label in window.grid_slaves():
            if int(label.grid_info()["row"]) == 5:
                label.grid_forget()
                label=Label(window,text="Please now login").grid(row=5,column=1)
            else:
                Label(window,text="Please now login").grid(row=5,column=1)


def login(loginName,loginPass,check):
    dic = load_obj('dic')
    login = False
    global attempts
    hashresRet=sha3_256((loginName+loginPass).encode('utf-8')).hexdigest()
    if login != True:
        try:
            if dic[int(hashresRet,16)] == {loginName:loginPass}:
                login = True
                for label in window.grid_slaves():
                    if int(label.grid_info()["row"]) == 4:
                        label.grid_forget()
                    elif int(label.grid_info()["row"]) == 5:
                        label.grid_forget()
                    elif int(label.grid_info()["row"])==7 and int(label.grid_info()["column"])==0:
                        label.grid_forget()
                successText="Welcome",loginName
                label=Label(window,text=successText).grid(row=5,column=0)
                if check == True:
                    save_obj([loginName,loginPass],"rememberName")
                    save_obj(check,"rememberFlag")
                else:
                    save_obj({},"rememberName")
                    save_obj(check,"rememberFlag")
                play = Button(window,text="Play",command=lambda:exitWindow(True))
                play.grid(row=5,column=1)
                Logout=Button(window,text="Logout",command=lambda:logout())
                Logout.grid(row=7,column=1)
            else:
                if attempts !=3:
                    for label in window.grid_slaves():
                        if int(label.grid_info()["row"]) == 5:
                            label.grid_forget()
                            tryAgainText="Please try again, You have "+str(3-attempts)+" attempts remaining"
                            Label(window,text=tryAgainText).grid(row=5,column=1)
                        else:
                            tryAgainText="Please try again, You have "+str(3-attempts)+" attempts remaining"
                            Label(window,text=tryAgainText).grid(row=5,column=1)
                else:
                    exitWindow(False)
                attempts +=1
        except:
            if attempts !=3:
                for label in window.grid_slaves():
                    if int(label.grid_info()["row"]) == 5:
                        label.grid_forget()
                        tryAgainText="Please try again, You have "+str(3-attempts)+" attempts remaining"
                        Label(window,text=tryAgainText).grid(row=5,column=1)
                    else:
                        tryAgainText="Please try again, You have "+str(3-attempts)+" attempts remaining"
                        Label(window,text=tryAgainText).grid(row=5,column=1)
            else:
                exitWindow(False)
            attempts +=1


#Module to logout
def logout():
    save_obj(False,"rememberFlag")
    save_obj({},"rememberName")
    global check
    for label in window.grid_slaves():
        if int(label.grid_info()["row"]) == 5:
            label.grid_forget()
            label=Label(window,text="Please now login").grid(row=5,column=1)
        elif int(label.grid_info()["row"])== 7 and int(label.grid_info()["column"]==1):
            label.grid_forget()
        else:
            Label(window,text="Please now login").grid(row=5,column=1)
    loginButton=Button(window,text="Login",command=lambda:login(UsernameBox.get(),PasswordBox.get(),check.get())).grid(row=4,column=0)
    createButton=Button(window,text="Create Account",command=lambda:create(UsernameBox.get(),PasswordBox.get())).grid(row=4,column=1)
    remember = Checkbutton(window,text="Remember me",variable=check)
    remember.grid(row=7,column=0)


#Module to close program         
def exitWindow(flag):
    if flag ==True:
        loggedInFlag=True
    else:
        loggedInFlag = False
    LoggedInFile = open(abspath(__file__).strip("src\\pyhton\\hash test.pyw")+"on\\src\\python\\obj\\LoggedIn.flg",'w')
    if loggedInFlag == True:
        LoggedInFile.write("true")
    else:
        LoggedInFile.write("false")
    LoggedInFile.close()
    window.destroy()
    exit()

    
#Check if "remember me" checkbox was checked on login since last log-off
if load_obj("rememberFlag") == True:
    loginDetail=load_obj("rememberName")
    login(loginDetail[0],loginDetail[1],True)

window.mainloop()
