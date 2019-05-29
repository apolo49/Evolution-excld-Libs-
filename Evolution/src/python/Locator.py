import os
def main(end):
    x=os.path.abspath(__file__).split("\\")
    for i in range(len(x)):
        x[i]=x[i]+"\\"
    x[len(x)-1]=x[len(x)-1].strip("\\")
    length=len(x)-1
    for i in range(length):
        if x[length-i] !="Evolution\\":
            x.remove(x[length-i])
        else:
            break
    x.append(end)
    y=""
    for i in x:
        y=y+i
    return y

