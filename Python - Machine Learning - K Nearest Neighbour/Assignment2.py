# -*- coding: utf-8 -*-
"""
Created on Sat Nov 26 16:51:59 2016

@author: Joel Satkauskas
         R00116315
         
         
"""


import numpy as np
import math
import operator 
from sklearn.metrics import confusion_matrix
import itertools
import matplotlib.pyplot as plt

# The path of the data
DataPath = 'C:\\Users\\Admin\\Desktop\\Abalone-Data.csv'

# Amount of neighbours to take vote of
knn = 5

# Amount of folds for cross fold validation
kFold = 5

# N used in distance-weighted Knn
DistanceWeightedN = 1

# The starting classification for one vs all
StartClassification = 3

# The ending classification for one vs all
EndClassification = 21
#removing out liiers/ not enough records to create a module on for those classifications.


"""
A method that will get the euclidean distance between 2 rows of the same structure.
"""
def euclideanDistance(row1, row2, length):
    distance = 0
    for x in range(length):
        distance += pow((row1[x] - row2[x]), 2)
    return math.sqrt(distance)

"""
A method that will get the manhatan distance between 2 rows of the same structure.
"""
def manhatanDistance(row1, row2, length):
    distance = 0
    for x in range(length):
        distance += abs(row1[x] - row2[x])
    return distance

"""
A method that will get the k nearest neightbours of a data instance
using the specified distance method.
Return: A list of the k nearest neighbours sorted by shortest distance.
"""
def getNeighbors(trainingSet, testInstance, k, distanceMethod):
    distances = []
    neighbors = []

    if distanceMethod == 1:
        for x in range(len(trainingSet)):
            dist = euclideanDistance(testInstance, trainingSet[x], len(testInstance)-1)
            distances.append((trainingSet[x], dist))
    else:
        for x in range(len(trainingSet)):
            dist = manhatanDistance(testInstance, trainingSet[x], len(testInstance)-1)
            distances.append((trainingSet[x], dist))
        
    distances.sort(key=operator.itemgetter(1))
    #print distances
    for x in range(k):
        neighbors.append(distances[x][0])
    return neighbors

"""
A method that will get the weighted distance of every neighbour of a data instance
using the specified distance method and return the class the highest value
Return: The classification that has the highest score.
"""
def getWeightedNeighbour(trainingSet, testInstance, distanceMethod):
    distances = []

    if distanceMethod == 1:
        for x in range(len(trainingSet)):
            dist = 1.0/pow((euclideanDistance(testInstance, trainingSet[x], len(testInstance)-1)), DistanceWeightedN)
            distances.append((trainingSet[x], dist))
    else:
        for x in range(len(trainingSet)):
            dist = 1.0/pow((manhatanDistance(testInstance, trainingSet[x], len(testInstance)-1)), DistanceWeightedN)
            distances.append((trainingSet[x], dist))
            
    classVotes = {}
    for x in range(len(distances)):
        response = distances[x][0][-1]
        if response in classVotes:
            classVotes[response] += distances[x][1]
        else:
            classVotes[response] = 0

    sortedVotes = sorted(classVotes.iteritems(), key=operator.itemgetter(1), reverse=True)
#    print sortedVotes
#    print ''
#    print ''
    return sortedVotes[0][0]

"""
A method that takes in the neighbours and return the
classification with the highest score(frequency)
Return: The classification with the highest score/appearance frequency.
"""
def getResponse(neighbors):
    classVotes = {}
    for x in range(len(neighbors)):
        response = neighbors[x][-1]
        if response in classVotes:
            classVotes[response] += 1
        else:
            classVotes[response] = 1
    sortedVotes = sorted(classVotes.iteritems(), key=operator.itemgetter(1), reverse=True)
    return sortedVotes[0][0]

"""
A method Takes in the testset and an array of their predictions and
composes an accuracy of the algorithm.
Return: The accuracy of the algorithm.
"""
def getAccuracy(testSet, predictions):
    correct = 0
    for x in range(len(testSet)):
        if testSet[x][-1] == predictions[x]:
            correct += 1
    return (correct/float(len(testSet))) * 100.0

"""
A method that will do 1 fold of the data set using the distance method indicated
and return the accuracy of the training set that was not used as a test set.
Return: The accuracy of the fold.
"""
def doKnnForFold(folds, data, testFold, knn, distanceMethod, drawCM):
    traingData = np.copy(data);
    testData = np.empty((0,8), float)
    predictions = []
    
    if testFold == 0:
        startAt = 0
    else:
        startAt = folds[testFold-1]
    
    for x in range(startAt, (folds[testFold])):
        testData = np.append(testData, [data[x,:]], 0)
    
    #print 'len of test data = ',  len(testData)
    
    traingData = np.delete(traingData, range(startAt, (folds[testFold])), 0)
    #print 'len of training data = ', len(traingData)
    
    for x in range(len(testData)):
        neighbors = getNeighbors(traingData, testData[x], knn, distanceMethod)
        result = getResponse(neighbors)
        predictions.append(result)
        #print('> predicted=' + str(result) + ', actual=' + str(testData[x][-1]))
    accuracy = getAccuracy(testData, predictions)
    #print('Accuracy: ' + "{0:.2f}".format(accuracy) + '%')

    #print predictions
    #print ''
    #print ''

    if drawCM:
        drawConfusionMatrix(testData, predictions)
    
    return accuracy

"""
A method that will set up the drawing of a confusion diagram.
Taken from scikit learn website.
return: none
"""
def plot_confusion_matrix(cm, classes,
                          normalize=False,
                          title='Confusion matrix',
                          cmap=plt.cm.Blues):
    """
    This function prints and plots the confusion matrix.
    Normalization can be applied by setting `normalize=True`.
    """
    plt.imshow(cm, interpolation='nearest', cmap=cmap)
    plt.title(title)
    plt.colorbar()
    tick_marks = np.arange(len(classes))
    plt.xticks(tick_marks, classes, rotation=45)
    plt.yticks(tick_marks, classes)

    if normalize:
        cm = cm.astype('float') / cm.sum(axis=1)[:, np.newaxis]
        print("Normalized confusion matrix")
    else:
        print('Confusion matrix, without normalization')

    print(cm)

    thresh = cm.max() / 2.
    for i, j in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
        plt.text(j, i, cm[i, j],
                 horizontalalignment="center",
                 color="white" if cm[i, j] > thresh else "black")

    plt.tight_layout()
    plt.ylabel('True label')
    plt.xlabel('Predicted label')

"""
A method that will in the test data and their predictions and draw
a confusion diagram.
return: none
"""
def drawConfusionMatrix(testData, predictions):
    realvalues = []
    label = []

    for x in range(len(testData)):
        realvalues.append(testData[x][-1])
        if testData[x][-1] not in label:
            label.append(testData[x][-1])

    classes = range(1, 28)
    #label.sort()

    # Compute confusion matrix
    cnf_matrix = confusion_matrix(realvalues, predictions)
    np.set_printoptions(precision=2)

    # Plot non-normalized confusion matrix
    plt.figure()
    plot_confusion_matrix(cnf_matrix, label,
                          title='Confusion matrix, without normalization')

    plt.show()

"""
A method that will do 1 fold of the data set using the distance weight method and
the distance method indicated and return the accuracy of the training
set that was not used as a test set.
Return: The accuracy of the fold.
"""
def doKnnDWForFold(folds, data, testFold, distanceMethod, drawCM):
    traingData = np.copy(data);
    testData = np.empty((0,8), float)
    predictions=[]

    if testFold == 0:
        startAt = 0
    else:
        startAt = folds[testFold-1]
    
    for x in range(startAt, (folds[testFold])):
        testData = np.append(testData, [data[x,:]], 0)
    
    #print 'len of test data = ',  len(testData)
    
    traingData = np.delete(traingData, range(startAt, (folds[testFold])), 0)
    #print 'len of training data = ', len(traingData)
    
    for x in range(len(testData)):
        neighbor = getWeightedNeighbour(traingData, testData[x], distanceMethod)
        predictions.append(neighbor)
        #print('> predicted=' + str(result) + ', actual=' + str(testData[x][-1]))
    accuracy = getAccuracy(testData, predictions)
    #print('Accuracy: ' + "{0:.2f}".format(accuracy) + '%')

    if drawCM:
        drawConfusionMatrix(testData, predictions)

    return accuracy
    
"""
A method that split the data into a binary classification.
Return: None but changes the data that was passed in.
"""
def DataOneVsMany(dataset, classification):
    for x in range(len(dataset)):
        if dataset[x][-1] != classification:
            dataset[x][-1] = 0
        # else:
        #     dataset[x][-1] = 0

"""
The method in charge of doing One vs All for each classification. It will split the data into
binary classifications for each classification then get the average accuracy of that classes
cross validation to get the accuracy of the model for a classification, then get the average
of each classification to get the average of the one vs all methodology.
Return: None -> worker method.
"""
def doOneVsAllWithKFold(classes, data, distanceMethod):
    print ''
    print ''
    print 'Doing One vs All Knn algorithm with:'
    if distanceMethod == 1:
        print '> Euclidean Distance'
    else:
        print '> Manhatan Distance'
    print '> Cross fold validation, k =', kFold
    
    dataRecords = {}

    for record in data:
        if record[-1] in dataRecords:
            dataRecords[record[-1]] += 1
        else:
            dataRecords[record[-1]] = 1
    
    print 'Records with classification'
    for key in dataRecords:
        print key, ' = ', dataRecords[key]
    
    classInt = StartClassification
    
    #sets the data for each class for one vs all
    for x in range(len(classes)):
        DataOneVsMany(classes[x], classInt)
        classInt += 1
        
    folds = []
    testFold = 0;
    foldResults = {}
    totalAccuracy = 0
    classAccuracy = []
    perFold = len(data)/kFold

    for x in range(kFold):
        if x == (kFold-1):
            folds.append(len(data))
        else:
            folds.append((perFold*(x+1)))    
    print 'Folds = ', folds
    
    for y in range(len(classes)):
        #print ''
        #print 'Classification: ', (y + StartClassification)
        print ''
        
        for x in range(kFold):
            #print ''
            #print '' + str(x+1) + ') Iteration'
            foldResults[(x+1)] = doKnnForFold(folds, classes[y], testFold, knn, distanceMethod, 0)
            testFold += 1
            
        for key in foldResults:
            totalAccuracy += foldResults[key]
            
        print ''
        print 'Classification: ', (y + StartClassification)
        print '\033[1m' + 'Average accuracy = ', str((totalAccuracy/kFold)) + '\033[0m'
#        print '\033[1m' + 'Knn = ', str(knn) + '\033[0m'
#        print '\033[1m' + 'k-Fold = ', str(kFold) + '\033[0m'
        print 'Records with this classification = ', dataRecords[y + StartClassification]
        
        classAccuracy.append(totalAccuracy/kFold)
        testFold = 0
        totalAccuracy = 0
        foldResults = {}
        
    print ''
    print ''
    print '---------------------------------------------------'
    print 'Finished all classes for One vs All'
    print('Accuracy: ' + "{0:.2f}".format(sum(classAccuracy)/len(classAccuracy)) + '%')
    print ''
    print 'Classifications = ', StartClassification, '-', EndClassification
    if distanceMethod == 1:
        print '> Euclidean Distance'
    else:
        print '> Manhatan Distance'
    print '\033[1m' + 'Knn = ', str(knn) + '\033[0m'
    print '\033[1m' + 'k-Fold = ', str(kFold) + '\033[0m'
    print '---------------------------------------------------'
    
"""
The method in charge of doing the normal Knn algorithm for the data set
using cross fold validation. It will run the algorithm for each fold and get the average.
Return: The average accuracy of the normal Knn algorithm on the abalone data set.
"""
def doNormalKnnWithKFold(data, distanceMethod, drawCM) :
    folds = []
    testFold = 0;
    foldResults = {}
    totalAccuracy = 0
    perFold = len(data)/kFold

    print ''
    print 'Doing normal Knn algorithm with :'
    if distanceMethod == 1:
        print '> Euclidean Distance'
    else:
        print '> Manhatan Distance'
    print '> Cross fold validation, k = ', kFold
    print '>', knn, 'Nearest Neighbors'

    for x in range(kFold):
        if x == (kFold-1):
            folds.append(len(data))
        else:
            folds.append((perFold*(x+1)))    
    print 'Folds = ', folds
    
    for x in range(kFold):
            print ''
            print 'Fold: ' + str(x+1)
            foldResults[(x+1)] = doKnnForFold(folds, data, testFold, knn, distanceMethod, drawCM)
            testFold += 1
            print('Accuracy: ' + "{0:.2f}".format(foldResults[(x+1)]) + '%')
            
    for key in foldResults:
        totalAccuracy += foldResults[key]
            
    print ''
    print '--------------------------------------------------------------'
    print 'Normal Knn'
    if distanceMethod == 1:
        print 'Euclidean Distance'
    else:
        print 'Manhatan Distance'
    print('Average accuracy = ' + "{0:.2f}".format(totalAccuracy/kFold) + '%')
    print '\033[1m' + 'Knn = ', str(knn) + '\033[0m'
    print '\033[1m' + 'k-Fold = ', str(kFold) + '\033[0m'
    print '--------------------------------------------------------------'
    print ''
    print ''
    
    return (totalAccuracy/kFold)

"""
The method in charge of doing the distance weighted Knn algorithm for the data set
using cross fold validation. It will run the algorithm for each fold and get the average.
Return:  The average accuracy of the distance weighted Knn algorithm on the abalone data set.
"""
def doWeightedKnnWithKFold(data, distanceMethod, drawCM):
    folds = []
    testFold = 0;
    foldResults = {}
    totalAccuracy = 0
    perFold = len(data)/kFold

    print ''
    print 'Doing Weighted-Distance Knn algorithm with :'
    if distanceMethod == 1:
        print '> Euclidean Distance'
    else:
        print '> Manhatan Distance'
    print '> Cross fold validation, k = ',kFold

    for x in range(kFold):
        if x == (kFold-1):
            folds.append(len(data))
        else:
            folds.append((perFold*(x+1)))    
    print 'Folds = ', folds
    
    for x in range(kFold):
            print ''
            print 'Fold: ' + str(x+1)
            foldResults[(x+1)] = doKnnDWForFold(folds, data, testFold, distanceMethod, drawCM)
            testFold += 1
            print('Accuracy: ' + "{0:.2f}".format(foldResults[(x+1)]) + '%')
            
    for key in foldResults:
        totalAccuracy += foldResults[key]
            
    print ''
    print '--------------------------------------------------------------'
    print 'Weighted-Distance Knn '
    if distanceMethod == 1:
        print 'Euclidean Distance'
    else:
        print 'Manhatan Distance'
    print('Average accuracy = ' + "{0:.2f}".format(totalAccuracy/kFold) + '%')
    print '\033[1m' + 'Knn = ', str(knn) + '\033[0m'
    print '\033[1m' + 'k-Fold = ', str(kFold) + '\033[0m'
    print '--------------------------------------------------------------'
    print ''
    print ''
    
    return (totalAccuracy/kFold)


def main():
    data = np.genfromtxt(DataPath, dtype=float, delimiter=',')
    data = np.delete(data, 0, 1)

    classes = []

    #copying data for OnevsAll

    Class3 = np.copy(data);
    classes.append(Class3)
    
    Class4 = np.copy(data);
    classes.append(Class4)
    
    Class5 = np.copy(data);
    classes.append(Class5)
    
    Class6 = np.copy(data);
    classes.append(Class6)
    
    Class7 = np.copy(data);
    classes.append(Class7)
    
    Class8 = np.copy(data);
    classes.append(Class8)
    
    Class9 = np.copy(data);
    classes.append(Class9)
    
    Class10 = np.copy(data);
    classes.append(Class10)
    
    Class11 = np.copy(data);
    classes.append(Class11)
    
    Class12 = np.copy(data);
    classes.append(Class12)
    
    Class13 = np.copy(data);
    classes.append(Class13)
    
    Class14 = np.copy(data);
    classes.append(Class14)
    
    Class15 = np.copy(data);
    classes.append(Class15)
    
    Class16 = np.copy(data);
    classes.append(Class16)
    
    Class17 = np.copy(data);
    classes.append(Class17)
    
    Class18 = np.copy(data);
    classes.append(Class18)
    
    Class19 = np.copy(data);
    classes.append(Class19)
    
    Class20 = np.copy(data);
    classes.append(Class20)
    
    Class21 = np.copy(data);
    classes.append(Class21)
    
#    Class22 = np.copy(data);
#    classes.append(Class22)
#    
#    Class23 = np.copy(data);
#    classes.append(Class23)
#    
#    Class24 = np.copy(data);
#    classes.append(Class24)
#    
#    Class25 = np.copy(data);
#    classes.append(Class25)
#    
#    Class26 = np.copy(data);
#    classes.append(Class26)
#    
#    Class27 = np.copy(data);
#    classes.append(Class27)
#    
#    Class28 = np.copy(data);
#    classes.append(Class28)
#    
#    Class29 = np.copy(data);
#    classes.append(Class29)

    #normalKnnAcuracyManhaten = doNormalKnnWithKFold(data, 2, 1)
    #normalKnnAcuracyEuclidean = doNormalKnnWithKFold(data, 1, 1)

    #DistanceWeightKnnAccuracyManhaten = doWeightedKnnWithKFold(data, 2, 1)
    #DistanceWeightKnnAccuracyEuclidean = doWeightedKnnWithKFold(data, 1, 1)

    doOneVsAllWithKFold(classes, data, 2)
    doOneVsAllWithKFold(classes, data, 1)
    

main()