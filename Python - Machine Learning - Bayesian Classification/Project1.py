# -*- coding: utf-8 -*-
"""
Authoer: Joel Satkauskas

"""

import os
import math
import re
from nltk.stem import PorterStemmer
from nltk.tokenize import sent_tokenize, word_tokenize

ps = PorterStemmer()

negPath = 'C:\\Users\\joel\\Desktop\\LargeIMDB\\neg\\'  
posPath = 'C:\\Users\\joel\\Desktop\\LargeIMDB\\pos\\'
testPosPath = 'C:\\Users\\joel\\Desktop\\smallTest\\pos\\'
testNegPath = 'C:\\Users\\joel\\Desktop\\smallTest\\neg\\'

stopWordPath = 'C:\\Users\\joel\\Desktop\\stopWords.txt'
stopWordFile = open(stopWordPath)
stopWordsStr = stopWordFile.read()
stopWordsList = stopWordsStr.split()

def removeStopWord(content):
    for word in content:
        if word in stopWordsList:
            content.remove(word)
    
    return content
    
def main():
    
    print '\n running....\n'
    
    testRegex = 'Welcome to regexr v2.1 sdf sdf sd,s d,f.sd fsdf, 3432 rr 32r3r3 e dfg dfg'
    print re.sub(r'[^a-zA-z\s]', '', testRegex)
    
    negListing = os.listdir(negPath)
    posListing = os.listdir(posPath)
    
    AllPosWords = []
    AllNegWords = []
    NegRecords = 0;
    PosRecords = 0;
    
    for eachFile in posListing:
        PosRecords+=1
        PosFile = open(posPath+eachFile, "r")
        PosContent = PosFile.read().decode('utf8')
        PosContent = PosContent.lower()
        #PosContent = PosContent.replace('"','').replace('.','').replace(',','').replace('!','').replace('?','').replace('(','').replace(')','').replace('-','').replace('_','').replace('--','').replace(';','').replace(':','').replace('/',' ').replace('\\',' ').replace('[','').replace(']','')
        PosContent = re.sub(r'[^a-zA-z\s]', '', PosContent)
        PosContent = ps.stem(PosContent)
        AllPosWords += (removeStopWord(PosContent.split()))
        
    for eachFile in negListing:
        NegRecords+=1
        NegFile = open(negPath+eachFile, "r")
        NegContent = NegFile.read().decode('utf8')
        NegContent  = NegContent.lower() 
        #NegContent  = NegContent .replace('"','').replace('.','').replace(',','').replace('!','').replace('?','').replace('(','').replace(')','').replace('-','').replace('_','').replace('--','').replace(';','').replace(':','').replace('/',' ').replace('\\',' ').replace('[','').replace(']','')
        NegContent  = re.sub(r'[^a-zA-z\s]', '', NegContent)
        NegContent = ps.stem(NegContent)        
        AllNegWords += (removeStopWord(NegContent.split()))
    
    print 'All pos words =',len(AllPosWords)
    print 'All neg words =',len(AllNegWords)
    
    print 'Amount of positive records = ', PosRecords
    print 'Amount of negative records = ', NegRecords
    
    vobalularyPos = set(AllPosWords)
    vobalularyNeg = set(AllNegWords) 
    vobalulary = vobalularyPos | vobalularyNeg
    
    print len(vobalulary),'In set'
    
    PNeg = (NegRecords+0.0)/(NegRecords+PosRecords)
    PPos = (PosRecords+0.0)/(NegRecords+PosRecords)
    
    print 'PNeg = ', PNeg
    print 'PPos = ', PPos
    
    posDict = {}
    negDict = {}
    
    for word in AllPosWords:
        if word in posDict:
            posDict[word] = posDict[word]+1
        else:
            posDict[word] = 1
    
    for word in AllNegWords:
        if word in negDict:
            negDict[word] = negDict[word]+1
        else:
            negDict[word] = 1
            
    for key in posDict:
        if key not in negDict:
            negDict[key] = 0
            
    for key in negDict:
        if key not in posDict:
            posDict[key] = 0
    
    print 'Dict Pos =',len(posDict)
    print 'Dict Neg =',len(negDict)
    print '\n'
    
    ProbPosDict = {}
    ProbNegDict = {}
    
    for word in posDict:
        ProbPosDict[word] = ((posDict[word]+1.0)/((len(AllPosWords)+len(vobalulary))))
        
    for word in negDict:
        ProbNegDict[word] = ((negDict[word]+1.0)/((len(AllNegWords)+len(vobalulary))))
        
#------------------Test data-------------------------------------
        
    negTestListing = os.listdir(testNegPath)
    posTestListing = os.listdir(testPosPath)
    PosTestRecords = 0
    NegTestRecords = 0
    TestAmountOfPosRight = 0
    TestAmountOfNegRight = 0
    
    for eachFile in posTestListing:
        PosTestRecords+=1
        PosTestFile = open(testPosPath+eachFile, "r")
        PosTestContent = PosTestFile.read().decode('utf8')
        PosTestContent = PosTestContent.lower()
        #PosTestContent = PosTestContent.replace('"','').replace('.','').replace(',','').replace('!','').replace('?','').replace('(','').replace(')','').replace('-','').replace('_','').replace('--','').replace(';','').replace(':','').replace('/',' ').replace('\\',' ').replace('[','').replace(']','')
        PosTestContent = re.sub(r'[^a-zA-z\s]', '', PosTestContent)
        PosTestContent = ps.stem(PosTestContent)
        AllTestPosWords = (removeStopWord(PosTestContent.split()))
        
        testPosTotal = math.log(PPos)
        testNegTotal = math.log(PNeg)
        
        for word in AllTestPosWords:
            if word in posDict:
                testPosTotal += math.log(ProbPosDict[word])
            if word in negDict:
                testNegTotal += math.log(ProbNegDict[word])
                
        if testPosTotal > testNegTotal:
            TestAmountOfPosRight+=1
            
    print 'Total pos file = ', PosTestRecords
    print 'Files predicted to be positive = ',TestAmountOfPosRight
    print 'Accuracy of model = ', (TestAmountOfPosRight*100.0/PosTestRecords)
                    

    for eachFile in negTestListing:
        NegTestRecords+=1
        NegTestFile = open(testNegPath+eachFile, "r")
        NegTestContent = NegTestFile.read().decode('utf8')
        NegTestContent  = NegTestContent.lower()
        #NegTestContent  = NegTestContent .replace('"','').replace('.','').replace(',','').replace('!','').replace('?','').replace('(','').replace(')','').replace('-','').replace('_','').replace('--','').replace(';','').replace(':','').replace('/',' ').replace('\\',' ').replace('[','').replace(']','')
        NegTestContent = re.sub(r'[^a-zA-z\s]', '', NegTestContent)        
        NegTestContent = ps.stem(NegTestContent)
        AllTestNegWords = (removeStopWord(NegTestContent.split()))
        
        testPosTotal = math.log(PPos)
        testNegTotal = math.log(PNeg)
        
        for word in AllTestNegWords:
            if word in posDict:
                testPosTotal += math.log(ProbPosDict[word])
            if word in negDict:
                testNegTotal += math.log(ProbNegDict[word])
                
        if testPosTotal < testNegTotal:
            TestAmountOfNegRight+=1
            
    print '\nTotal neg file = ', NegTestRecords
    print 'Files predicted to be negative = ',TestAmountOfNegRight
    print 'Accuracy of model = ', (TestAmountOfNegRight*100.0/NegTestRecords)
     
    print '\nAverage accurecy = ', ((TestAmountOfNegRight+TestAmountOfPosRight)*100.0)/(PosTestRecords+NegTestRecords)
                
main()
