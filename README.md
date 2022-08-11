# My Personal Project

## Project Proposal
 
The project I'm working on is a **wifi password tracker**. Users can enter the wifi password of different locations.
Possible features include lists of devices associated with specific networks, lists of people who know the specific wifi password, 
as well as password editing and deletion. 

### What will this application do?
The key purpose of this application is to make it easier to remember wifi passwords. Users will be able to save wifi passwords
as well as data associated with the passwords to make it easier for them to remember it. They will be able to 
edit passwords and metadata associated with the password if they ever change, as well as delete them.

### Who will use it?
Anyone who has trouble remembering wifi passwords (like myself) will use this.

### Why is it of interest to me?
I often have trouble remembering the wifi password at my own places, or at friend's homes and
having to ask people for it is a tedious task. I want to have the wifi password wherever I go stored somewhere,
so I'm building this application.

There are a few key indicators that I believe should be associated with a password in order to make them 
easier to track.
- A name to associate the password with
- An address to reference with the password
- The password itself
- A list of devices that are often connected to a specific network

## User Stories
- As a user, I want to be able to add wifi passwords to a list of wifi passwords
- As a user, I want to be able to associate my password with a name
- As a user, I want to be able to add an address to my password
- As a user, I want to be able to add a list of devices that are associated with the network
- As a user, I want to be able to add a list of people who know the wifi password
- As a user, I want to be able to delete passwords I'm no longer using
- As a user, I want to be able to edit passwords and their metadata
- As a user, I want to be able to view the password data associated with a name
- As a user, whenever I add a new password, I want that password saved to a file
- As a user, I want to be able to load my passwords from the file I saved them to
- As a user, I want my edits to a password to be saved in the file that the password is stored

# Instructions for Grader
### Generate "Add multiple X's to Y"
- Fill out input fields
- Click "Add" button
New password should be visible in list of passwords, repeat as many times as you want (cannot add passwords with
- duplicate names)
### Find visual component:
- Click "Save"
- Enjoy the meme (enlarge window to see the whole gif)
### Saving and Loading data
- To save click "save", to load click "load" and JSON-stored passwords will be added to list
### 2 Pieces of Extra functionality
- Click on any of the passwords you see in the list - the text on the right should dynamically update with 
the metadata of the selected password
- Hit the "Display panel" button to open a new popup with the current passwords information

### Example LogPrinter output 
- Actions: added, deleted password

`  Thu Aug 11 14:00:21 PDT 2022
  Password added to list`

  `Thu Aug 11 14:00:22 PDT 2022
  Password removed`
  
# Reflection

The most significant refactor I would do is in the UI class. For the most-part, my models are well done (clean code, 
well documented, readable) although I would make it more robust and add exception handling. My GUI is currently one massive file 
that is over 500 lines of code long, poorly documented and is a mess to look through. I would extract the UI components into their own 
custom classes as well as significantly reorganize the functions wihtin the main GUI class.