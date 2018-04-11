from google_images_download import google_images_download   #importing the library

response = google_images_download.googleimagesdownload()   #class instantiation

file = open("Veale's fictional worlds.txt", "r") 
for line in file:
	world = line.split("\t")[0]
	try:
		arguments = {"keywords":world,"limit":1,"print_urls":False, "output_directory":"C:\\Users\\user\\Desktop\\worlds\\"}    #creating list of arguments
		response.download(arguments)
	except:
		print(world+"\n")