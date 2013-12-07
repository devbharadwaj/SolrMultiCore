########################################################################
#   Analyze a Wiki XML Dump file and find out 
#   The average size of text			      	
#   Median value of text size
#   These numbers help in getting rid of small documents	
######################################################################## 

import sys
import os
import re

size = 0
count = 0
adder = 0
str = ''
median = []
redalert = 0
filename = 'wikinewsdump.xml'

for line in open(filename, 'r'):
	if ( line.find('<redirect') != -1):
		redalert = 1
	if ( line.find('<text') != -1 and redalert == 0):
		adder = 1
		str = str + line
		count = count + 1
	if ( adder == 1 and line.find('</text>') == -1):
		str = str + line
	if ( adder == 1 and line.find('</text>') != -1):
		str = str + line
		adder = 0
		size = size + str.__len__()
		median.append(str.__len__())
		str = ''
		redalert = 0
	if ( line.find('</text>') != -1 ):
		redalert = 0

median.sort()
print 'Average doc length: ', size/count,'\n Median: ', median[count/2]
print 'Doc count', count
print 'Successful'
