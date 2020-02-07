from py_compile import compile as cmp
import os


f = []


for (dirpath, dirnames, filenames) in os.walk(os.getcwd()):
    f.extend(filenames)
    break
pyFiles = []


for i in f:
    if i[-2:] == "py" or i[-3:] == "pyw":
        if i[-2:] != "pyc" or i[-3:] != "pyc":
            pyFiles.append(i)

try:
    os.mkdir(os.getcwd()+"\\compiled files")
except:
    pass


fileNames=[]
for i in pyFiles:
    if i[-2:] == "py":
        fileNames.append(i[:-2]+"pyc")
    elif i[-3:] == "pyw":
        fileNames.append(i[:-3]+"pyc")


for i in range(0,len(pyFiles)-1):
    cmp(pyFiles[i],os.getcwd()+"\\compiled files\\"+fileNames[i])
