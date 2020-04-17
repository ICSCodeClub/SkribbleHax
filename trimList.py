from PyDictionary import PyDictionary
import re, string


# Deletes all words that aren't the designated part of speech,
# as dictated by PyDictionary. Returns these words as a list with no punctuation
DICTIONARY = PyDictionary()
def getPartOnly(words,partOfSpeech):
    partOfSpeech = '\''+str(partOfSpeech)+'\''
    words = re.sub('['+string.punctuation+']', '', str(words)).split()
    
    acceptedWords = []
    for word in words:
        meaning = ""
        with HiddenPrints():
            meaning = str(DICTIONARY.meaning(word))
        
        if not (meaning is "None") and len(meaning) > len(partOfSpeech):
            if partOfSpeech in meaning: #hacky, but works
                acceptedWords.append(word)
    return acceptedWords

def updateFile(filePath):
    words = ''
    with open(filePath,'r') as file:
        words = str(file.read()).replace('\n',' ')
    print("found: "+str(words))
    listNouns = getPartOnly(words, 'Noun')
    with open('mod'+filePath,'w') as file:
        file.write("\n".join(listNouns))

# From https://stackoverflow.com/questions/8391411/suppress-calls-to-print-python
# PyDictionary LOVES to print garbage
import os, sys
class HiddenPrints:
    def __enter__(self):
        self._original_stdout = sys.stdout
        sys.stdout = open(os.devnull, 'w')

    def __exit__(self, exc_type, exc_val, exc_tb):
        sys.stdout.close()
        sys.stdout = self._original_stdout



updateFile("list2.txt")
