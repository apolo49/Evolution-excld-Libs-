#imports
from hashlib import sha3_256
from pickle import dump,HIGHEST_PROTOCOL,load
from urllib.request import urlopen
from lib.PIL import Image as OpenImage,ImageTk
from tkinter import Tk, BooleanVar,Label,Entry,Button,Checkbutton,CENTER
import Locator
#end imports


attempts = 0 #global variable attempts

#entire code function to encapsulate the loop for the screen
def main():
    #General Locator
    def save_obj(obj, name): #to save an object and data type
        with open(Locator.main("src\\python\\obj\\"+ name + '.pkl'), 'wb') as f: #open the file as f and in overwrite binary mode
            dump(obj, f, HIGHEST_PROTOCOL) #dump the variable and its data to the file
            f.close() #close the file

    def load_obj(name): #to load a file
        try: #If error will be because file doesnt exist and so create the file with the error if try doesnt work
            with open(Locator.main("src\\python\\obj\\" + name + '.pkl'), 'rb') as f: #open file to be read
                return load(f) #return the contents
                f.close() #close the file
        except:
            save_obj({},name) #create the file and make it empty
            load_obj(name)#load the file as an output is expected
    print(load_obj("dic"))
    LoggedInFile = open(Locator.main("src\\python\\obj\\LoggedIn.flg"),'w') #grab the path for the logged in file and write false to it
    LoggedInFile.write("false") 
    LoggedInFile.close() #Close the file

    #End general Locator


    #Locator window
    window = Tk() #Create a window
    window.title("Login") #set title of window to "login"
    pathToImage = Locator.main("res\\textures\\Small Logo.png") #Find the small version of the logo
    pathToBack = Locator.main("res\\textures\\stone.png") #Find path to the background of the launcher
    img = ImageTk.PhotoImage(OpenImage.open(pathToImage).resize((256,82),OpenImage.ANTIALIAS)) #Resize the small logo
    back = ImageTk.PhotoImage(OpenImage.open(pathToBack).resize((512,400),OpenImage.ANTIALIAS)) #Resize the background image
    check = BooleanVar() #Create the variable for the checkbox

    try: #Try to download the changelog for the game
        dataFile = urlopen("https://www.dropbox.com/s/18mqoxgg85m20at/logs.txt?dl=1") #open the link to the file to download
        data = dataFile.read() #read the file
        dataFile.close() #close the file
    except: #If it fails to dowload
        data="Please connect to the interwebs for changelog" #Set the text to "Please connect to the interwebs for changelog"
        
    #Labels
    Label(window,image=img).grid(row=0,column=0,columnspan=2) #Adds the logo to the top left of the launcher and makes it span 2 columns
    Label(window,image=back,text=data, compound=CENTER,fg="white").grid(row=1,column=0,columnspan=2) #Adds the background of the launcher to the launcher and adds the text to it and colours the text to white and makes the background span two columns
    Label(window,text="Username: ").grid(row=2,column=0) # Adds a label saying "Username: " and adds to the window at row 2 column 0
    Label(window,text="Password: ").grid(row=3,column=0) # Adds a label saying "Password: " and adds to the window at row 3 column 0

    #Entry boxes
    UsernameBox=Entry(window,width=40) #Creates a username box and makes it a child to the window and make a width of 40
    UsernameBox.grid(row=2,column=1) #adds the box to the grid at row 2, column 1
    PasswordBox=Entry(window,show="*",width=40) #Creates a password box and makes it a child to the window and make a width of 40
    PasswordBox.grid(row=3,column=1) # adds the box to the grid at row 3, column 1

    #Default buttons
    loginButton=Button(window,text="Login",command=lambda:login(UsernameBox.get(),PasswordBox.get(),check.get())).grid(row=4,column=0) #create a login button that is the child of the window and have it execute the command to run the login function and grab the contents of the login box and password box and the state of the check box when clicked
    createButton=Button(window,text="Create Account",command=lambda:create(UsernameBox.get(),PasswordBox.get())).grid(row=4,column=1) #Create a button that is the child of the window and when clicked execute the command to run the create account function and grab the contents of the login box and password box
    remember = Checkbutton(window,text="Remember me",variable=check) #Create a check button with the text "remember me", with the state variable check which is a boolean
    remember.grid(row=7,column=0) #Add the check box to the window at the row 7 and 0th column

    #end window Locator


    def create(userN,Pass): #Create account module; takes in variable UserName and Password
        dic = load_obj('dic') #load the datatbase of Username and passwords
        hashres=sha3_256((userN+Pass).encode('utf-8')).hexdigest() #generate a hash value for the string of both username and password
        if int(hashres,16) not in dic: #if the hash is not in the dictionary
            dic[int(hashres,16)] = {userN:Pass} #then add the username and password to the dictionary at the point where the integer conversion of the hash value
            for label in window.grid_slaves(): #go through all the elements in the window and call the variable "label"
                if int(label.grid_info()["row"]) == 5: #if the element is in row 5 of the window
                    label.grid_forget() #Then forget the element
                    Label(window,text="Please now login").grid(row=5,column=1) #Add a label that reads "Please now login" at row 5, column 1
                else:
                    Label(window,text="Please now login").grid(row=5,column=1) #Otherwise add the label at that spot anyway
        else: #If the hash is in the dictionary
            if str({userN:Pass}==dic[int(hashres,16)]): #If the username and password is equal to the string at that spot
                for label in window.grid_slaves(): #Then go through the labels in the window
                    if int(label.grid_info()["row"]) == 5: #remove any labels at row 5
                        label.grid_forget()
                        Label(window,text="User already exists").grid(row=5,column=1) #Add a label that reads "User already exists" and place it at row 5, column 1
                    else:
                        Label(window,text="User already exists").grid(row=5,column=1) #place that label there anyway
            else:
                dic[int(hashres,16)+1] = {userN:Pass} #Otherwise place it in the next available slot
        if dic != load_obj('dic'): #If the current local dictionary is not equal to the dictionary in the file
            save_obj(dic,'dic') #Overwrite the dictionary in the file with the new dictionary
            for label in window.grid_slaves(): #go through all elements in the window
                if int(label.grid_info()["row"]) == 5: #if there is any elements in row 5
                    label.grid_forget() #forget the element
                    Label(window,text="Please now login").grid(row=5,column=1)#add a label saying "please now login" at row 5, column 1
                else: #Otherwise add the label there anyway
                    Label(window,text="Please now login").grid(row=5,column=1) 


    def login(loginName,loginPass,check):
        dic = load_obj('dic') #loading in the dictionary
        login = False #set value as default value False
        global attempts #calling in the attempts value
        hashresRet = sha3_256((loginName+loginPass).encode('utf-8')).hexdigest() #hash result comparison value
        if login != True: #Master login check
            try: #If fail then auto cancel login and remove an attempt
                if dic[int(hashresRet,16)] == {loginName:loginPass}: #comapring result to input
                    login = True #Set login to true as the username and password are in the database
                    for label in window.grid_slaves():#go through all labels in the window and remove at row = 2,3,4,5, and at 7 if column = 0
                        if int(label.grid_info()["row"]) == 2:
                            label.grid_forget()
                        elif int(label.grid_info()["row"]) == 3:
                            label.grid_forget()
                        elif int(label.grid_info()["row"]) == 4:
                            label.grid_forget()
                        elif int(label.grid_info()["row"]) == 5:
                            label.grid_forget()
                        elif int(label.grid_info()["row"]) == 7 and int(label.grid_info()["column"]) == 0:
                            label.grid_forget()
                    successText = "Welcome",loginName #Generate text to welcome the user
                    Label(window,text=successText).grid(row=5,column=0) #Attach the text to the window
                    if check == True: #If they asked to remember login details
                        save_obj([loginName,loginPass],"rememberName") #save the details of the user in a file 
                        save_obj(check,"rememberFlag") #set remember flag to true
                    else:
                        save_obj({},"rememberName") #Set remember file to blank so no user gets remembered
                        save_obj(check,"rememberFlag") #set flag to blank
                    play = Button(window,text="Play",command=lambda:exitWindow(True)) #create play button
                    play.grid(row=5,column=1) #Put the play button on grid
                    Logout = Button(window,text="Logout",command=lambda:logout()) #Create logout button
                    Logout.grid(row=7,column=1) #put logout on grid
                else: #if login details not in database
                    if attempts != 3: #if the number of attempts used is not 3
                        for label in window.grid_slaves(): #go through all labels in the window
                            if int(label.grid_info()["row"]) == 5: #remove label if there is a label at row 5
                                label.grid_forget()
                                tryAgainText = "Please try again, You have "+str(3-attempts)+" attempts remaining" #Update the text to show the correct number of attempts
                                Label(window,text=tryAgainText).grid(row=5,column=1) #Put the text to a label and add it to the window
                            else: #If there is no label at row 5
                                tryAgainText = "Please try again, You have "+str(3-attempts)+" attempts remaining" #Update text to show correct number of attempts
                                Label(window,text=tryAgainText).grid(row=5,column=1) #Put the text to a label and add it to the window
                    else: #If the number of the attempts used is 3
                        exitWindow(False) #Exit the window with the logged in flag as false and close the program so the game wont run
                    attempts += 1 #Add one to the number of given attempts
            except: #If there is an error (repeat above for false login)
                if attempts != 3:
                    for label in window.grid_slaves():
                        if int(label.grid_info()["row"]) == 5:
                            label.grid_forget()
                            tryAgainText = "Please try again, You have "+str(3-attempts)+" attempts remaining"
                            Label(window,text=tryAgainText).grid(row=5,column=1)
                        else:
                            tryAgainText = "Please try again, You have "+str(3-attempts)+" attempts remaining"
                            Label(window,text=tryAgainText).grid(row=5,column=1)
                else:
                    exitWindow(False)
                attempts += 1


    #Module to logout
    def logout():
        save_obj(False,"rememberFlag") #Update the flag to remember a user to false
        save_obj({},"rememberName") #Update the details of a remembered user to empty
        for label in window.grid_slaves(): #Go through all labels in window
            if int(label.grid_info()["row"]) == 5: #If the row of the label is = 5
                label.grid_forget() #Remove label on window
                Label(window,text="Please now login").grid(row=5,column=1) #Replace label as "Please now login"
            elif int(label.grid_info()["row"])== 7 and int(label.grid_info()["column"]==1): #If label is at row 7 and is in column 1
                label.grid_forget() #remove label
            else:
                Label(window,text="Please now login").grid(row=5,column=1) #add label at row 5 and column 1
        Label(window,text="Username: ").grid(row=2,column=0)#Add label "Username: " at row 2 column 0
        Label(window,text="Password: ").grid(row=3,column=0)#Add label "Password: " at row 3 column 0
        UsernameBox = Entry(window,width=40) #Add an entry box for the username and add it to the window as a child
        UsernameBox.grid(row=2,column=1) #Set the box to row 2 column 1 (next to the text)
        PasswordBox = Entry(window,show="*",width=40) #Add an entry box for the password and add it to the window as a child
        PasswordBox.grid(row=3,column=1) #Set the box to row 3 column 1 (next to the text)
        loginButton = Button(window,text="Login",command=lambda:login(UsernameBox.get(),PasswordBox.get(),check.get())).grid(row=4,column=0) #Create a button to login with the command to go to the login function and grab the entry to the username box and password box and state of the remember me button at row 4 column 1
        createButton = Button(window,text="Create Account",command=lambda:create(UsernameBox.get(),PasswordBox.get())).grid(row=4,column=1) #Create a button to create an account from the entries to the password box and username box at row 4 column 1
        check = BooleanVar() #Create the check variable for the checkbox
        remember = Checkbutton(window,text="Remember me",variable=check) #Create a check box to remember the login of the user
        remember.grid(row=7,column=0) #add the check box to row 7, column 0


    #Module to close program         
    def exitWindow(flag):
        if flag == True: #If the login flag = True the set a local variable, else false
            loggedInFlag = True
        else:
            loggedInFlag = False
        LoggedInFile = open(Locator.main("src\\python\\obj\\LoggedIn.flg"),'w') #open the logged in flag file for the java code with the write attribute
        if loggedInFlag == True: #if the local variable is True
            LoggedInFile.write("true") #Override the current contents of the file to read "true"
        else:#If it is not true
            LoggedInFile.write("false") #Override the current contents of the file to read "false"
        LoggedInFile.close() #Close the file
        window.destroy() #destroy the window and all children
        exit() #close the program

        
    #Check if "remember me" checkbox was checked on login since last log-off
    if load_obj("rememberFlag") == True: #Check if the file reads true
        loginDetail=load_obj("rememberName") #if the flag is true then grab the login details
        login(loginDetail[0],loginDetail[1],True) #login the user and set the remember me flag to true

    window.mainloop() #Loop the window to keep it updated

    
main() #run the program
