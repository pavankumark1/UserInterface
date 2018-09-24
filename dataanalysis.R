##setting path to the data
setwd("G:/GVSU/3rd Sem (Spring)/CIS 660 - Information Management and Science/Project/R analysis")

getwd()

traffic <- read.csv("Traffic_Violations.csv", header = T, sep = ",")


#removing potential unwanted columns

traffic2 <- traffic[,-c(2,3,4,6,7,8,10,11,12,14,15,16,17,25,26,27,31,32,34,35)]

traffic2 <- na.omit(traffic2) #removes any NA values if present 

colnames(traffic2)

#extracting the date column
date <- data.frame(traffic2[,1])

#as the data set has two types of seperators for date, I have replaced it with one type 
#by using this function
date <- gsub("/","-",date$traffic2...1.)

date <- data.frame(date)

typeof(date)

date <- data.frame(as.character(as.Date(date$date,"%m-%d-%Y")))

colnames(date)<- "Date"

traffic3 <- cbind(date,traffic2[,-1])

UID <- data.frame(1:nrow(traffic3))

colnames(UID)<- "UID"

traffic4 <- cbind(UID,traffic3)

#saving data to a CSV file
write.csv(traffic4, "traffic.csv", row.names = F)
colnames(traffic4)

