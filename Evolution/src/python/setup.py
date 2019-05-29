from os.path import abspath
def main(end):
    x=abspath(__file__).split("\\")
    for i in range(len(x)):
        x[i]=x[i]+"\\"
    x[len(x)-1]=x[len(x)-1].strip("\\")
    for i in range(len(x)):
        if x[(len(x)-1)-i] !="Evolution\\":
            x.remove(x[(len(x)-1)-i])
        else:
            break
    x.remove(x[len(x)-1])
    x.append(end)
    y=""
    for i in x:
        y=y+i
    return y

