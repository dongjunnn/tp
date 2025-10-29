---
layout: page
title: User Guide
---

IndiDex is a **desktop contact and project management application designed for indie content creators**, optimised for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, IndiDex can help you manage your content creator network and projects faster than traditional GUI apps. Designed for creators managing 20+ collaborators, IndiDex lets you store all contact details (including social media profiles like Discord, Instagram, YouTube, and LinkedIn), create projects with deadlines, and assign team members—all in seconds rather than minutes.

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

# Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-W11-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for IndiDex.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar indidex.jar` command to run the application.
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data (the screenshot contains added projects which won't appear on launch).<br>
   ![image](https://hackmd.io/_uploads/HyrkrTP0gx.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pr/HIGH dc/johndoe#1234` : Adds a contact named `John Doe` with high priority and Discord handle to IndiDex.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `padd n/Web Series Project d/2025-12-31 pr/HIGH m/1 2 3` : Creates a new project with deadline and assigns the first 3 contacts as team members.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

* **INDEX** refers to the index number shown in the displayed contact list. The index **must be a positive integer** 1, 2, 3, …​

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

## Contacts

### Adding a contact: `add`

Adds a contact to IndiDex.

Format: `add n/NAME p/PHONE e/EMAIL a/ADDRESS [pr/PRIORITY] [dc/DISCORD] [li/LINKEDIN] [ig/INSTAGRAM] [yt/YOUTUBE] [t/TAG]…​`

* **Required fields:**
    * `n/NAME` - Contact's full name
    * `p/PHONE` - Phone number (minimum 3 digits)
    * `e/EMAIL` - Valid email address
    * `a/ADDRESS` - Physical or mailing address

* **Optional fields:**
    * `pr/PRIORITY` - Priority level: `LOW`, `MEDIUM`, or `HIGH`, defaults to `LOW`
    * `dc/DISCORD` - Discord handle/username
    * `li/LINKEDIN` - LinkedIn profile URL
    * `ig/INSTAGRAM` - Instagram handle
    * `yt/YOUTUBE` - YouTube channel URL
    * `t/TAG` - Tags for categorizing contacts (can add multiple tags)

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A contact can have any number of tags (including 0). Social media profiles are optional and will only display if provided.
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
IndiDex prevents duplicate contacts. You cannot add a contact with the same name, phone, and email as an existing contact.
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Alex Tan p/87654321 e/alex@example.com a/Clementi Ave 2 pr/HIGH dc/alextanyt#1234 ig/@alextan yt/youtube.com/alextanchannel t/animator t/friend`
* `add n/Sarah Lee p/91234567 e/sarah@creator.com a/Bukit Timah Road pr/MEDIUM li/linkedin.com/in/sarahlee t/musician`

### Listing all contacts : `list`

Shows a list of all contacts in IndiDex.

Format: `list`

* Displays all contacts in the address book
* Resets any active search filters applied by the `find` command

### Editing a contact : `edit`

Edits an existing contact in IndiDex.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pr/PRIORITY] [dc/DISCORD] [li/LINKEDIN] [ig/INSTAGRAM] [yt/YOUTUBE] [t/TAG]…​`

* Edits the contact at the specified `INDEX`.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the contact will be removed i.e adding of tags is not cumulative.
* You can remove all the contact's tags by typing `t/` without specifying any tags after it.
* You can remove social media profiles by providing a '-' as input (e.g., `dc/-` to remove Discord handle).

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Editing a contact to have the same details as another existing contact will be rejected to prevent duplicates.
</div>

Examples:
* `edit 1 p/91234567 e/johndoe@example.com` - Edits the phone number and email address of the 1st contact.
* `edit 2 n/Betsy Crower t/` - Edits the name of the 2nd contact and clears all existing tags.
* `edit 3 pr/HIGH dc/newhandle#5678 ig/@newinsta` - Updates priority and social media profiles of the 3rd contact.

### Finding contacts: `find`

Finds contacts whose details match any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Searches across **name, phone number, and email address**
* Note that the following fields have different matching criteria:
    * Name has partial matching. E.g. `find Alex` and `find Alex Tan` can both return `Alex Tan`
    * You have to enter the entire phone number`find PHONENUM` to search for it
    * Email has partial matching. Either find the entire email e.g. `john@gmail.com` or enter the email domain to find corresponding addresses e.g. `@gmail.com`
* Contacts matching at least one keyword will be returned (i.e. `OR` search).
* To search specifically for email addresses, make sure to always include the `@` symbol in your keyword

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`
* `find 12349876` returns the specific contact with phone number 12349876
* `find @gmail.com` returns all contacts with Gmail email addresses

### Tagging contacts: `tag`

Adds tags to one or more contacts simultaneously.

Format: `tag INDEX [MORE_INDICES] t/TAG [t/MORE_TAGS]…​`

* Tags the contacts at the specified indices
* Multiple contacts can be tagged in one command
* Multiple tags can be added at once
* Tags are **added to existing tags** (cumulative operation)
* All existing tags are retained

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use the `tag` command to quickly categorize multiple contacts at once instead of editing them individually.
</div>

Examples:
* `tag 1 t/client` - Adds the "client" tag to the 1st contact
* `tag 1 2 3 t/collaborator t/2025` - Adds "collaborator" and "2025" tags to contacts 1, 2, and 3
* `tag 5 7 t/urgent` - Adds "urgent" tag to the 5th and 7th contacts

### Deleting contacts : `delete`

Deletes the specified contact(s) from IndiDex.

Format: `delete INDEX [MORE_INDICES]`

* Deletes the contact(s) at the specified index/indices
* Multiple contacts can be deleted in one command
* The index refers to the index number shown in the displayed contact list

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This operation cannot be undone. Make sure you are deleting the correct contact(s).
</div>

Examples:
* `delete 2` deletes the 2nd contact in IndiDex.
* `find Betsy` followed by `delete 1` deletes the 1st contact in the results of the `find` command.
* `delete 3 5 7` deletes the 3rd, 5th, and 7th contacts in the current displayed list.

### Sorting contacts : `sort`

Sort the contact(s) from IndiDex by exactly one chosen attribute.

Format: `sort (n/|p/|a/|pr/|e/)[asc/desc]`

* Sorts the contacts by the specified attribute
* Defaults to ascending order
* Alphanumerical attributes are sorted by their unicode values

Examples:
* `sort n/asc` sorts contacts by name in alphabetical order
* `sort pr/` sorts contacts by ascending priority (from LOW to HIGH)
* `sort p/desc` sorts contacts by phone numbers in descending numerical order


## Projects

### Adding a project: `padd`

Creates a new project in IndiDex and assigns team members to it.

Format: `padd n/PROJECT_NAME d/DEADLINE pr/PRIORITY [m/MEMBER_INDEX]…​`

* **Required fields:**
    * `n/PROJECT_NAME` - Name of the project (must be unique)
    * `d/DEADLINE` - Project deadline in `yyyy-MM-dd` format (e.g., 2025-12-31)
    * `pr/PRIORITY` - Priority level: `LOW`, `MEDIUM`, or `HIGH`

* **Optional fields:**
    * `m/MEMBER_INDEX` - Index of contacts to add as project members (can specify multiple)

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can view all your projects in the project panel on the right side of the application. Contacts assigned to projects will show clickable project links on their contact cards.
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Project names are case-sensitive and must be unique. You cannot create two projects with the same name.
</div>

Examples:
* `padd n/Web Series Pilot d/2025-06-30 pr/HIGH` - Creates a high priority project with deadline
* `padd n/Animation Collab d/2025-12-31 pr/MEDIUM m/1 2 3` - Creates a project and assigns contacts 1, 2, and 3 as members
* `padd n/Podcast Season 2 d/2025-09-15 pr/LOW m/5` - Creates a low priority project with one team member

### Joining a project: `join`

Adds the specified members to the chosen project.

Format: `join n/PROJECT_NAME m/MEMBER_INDEX [m/MEMBER_INDEX]...`

* Adds the contacts at the specified indices to the named project
* Specifying projects is **case insensitive**
* Adding existing members has no effect but are allowed only if there is at least one new member joining

Examples:
* `join n/Shopee Advertisement m/3` - Adds the third contact in the list to the project 'Shopee Advertisement'
* `join m/6 m/7 n/soc vlog` - Adds the sixth and seventh contact in the list to the project 'SoC Vlog'

### Leaving a project: `leave`

Removes the specified members to the chosen project.

Format: `leave n/PROJECT_NAME m/MEMBER_INDEX [m/MEMBER_INDEX]...`

* Removes the contacts at the specified indices to the named project
* Specifying projects is **case insensitive**
* Contacts who are not members are not allowed to leave
* Contacts are not allowed to leave if there will be no member remaining in the project

Examples
* `leave n/Shopee Advertisement m/2` - Removes the second contact from the project if they are a member
* `leave m/5 m/6 n/soc vlog` - Removes the fifth and sixth contact from the project

### Deleting a project: `pdelete`

Removes a project from IndiDex.

Format: `pdelete PROJECT_NAME`

* Deletes the project with the exact matching name
* Project name matching is **case-sensitive**
* Must be an exact match of the project name

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This operation cannot be undone. The project name must match exactly (case-sensitive).
</div>

Examples:
* `pdelete n/Web Series Pilot` - Deletes the project named "Web Series Pilot"
* `pdelete n/Animation Collab` - Deletes the project named "Animation Collab"

## Others

### Clearing all entries : `clear`

Clears all entries (both persons and projects) from IndiDex.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This will delete ALL contacts and projects. This operation cannot be undone. Make sure to backup your data before using this command.
</div>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

IndiDex data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

IndiDex data are saved automatically as a JSON file `[JAR file location]/data/indidex.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, IndiDex will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause IndiDex to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## Field Constraints and Validation

### Name
* Can contain any characters
* Cannot be blank

### Phone Number
* Must contain at least 3 digits
* Only numeric characters are allowed
* No spaces or special characters

### Email Address
* Must follow the format: `local-part@domain`
* **Local part** (before @):
    * May contain alphanumeric characters and these special characters: `+` `_` `.` `-`
    * Cannot start or end with special characters
* **Domain** (after @):
    * Must consist of domain labels separated by periods
    * Each domain label must:
        * Start and end with alphanumeric characters
        * Consist of alphanumeric characters and hyphens only
    * Last domain label must be at least 2 characters long

### Address
* Can contain any characters
* Cannot be blank

### Priority
* Must be one of: `LOW`, `MEDIUM`, or `HIGH`
* Not case-sensitive (can be lowercase or uppercase)

### Social Media Profiles
* **Discord**: In the format username#1234, where username is 3-32 alphanumeric characters and discriminator is exactly 4 digits
    * Example: `johndoe#1234`
* **LinkedIn**: LinkedIn profile URL starting with '(https://(www.)linkedin.com/in/' followed by profile identifier
    * Example: `linkedin.com/in/johndoe`
* **Instagram**: Instagram handle starting with '@' and be 1-30 alphanumeric characters or underscores.
    * Example: `@johndo`
* **YouTube**: YouTube channel URL starting with '(https://(www.))youtube.com/'
    * Example: `www.youtube.com/johndoe`
* All social media fields are optional

### Tags
* Alphanumeric characters only (no spaces or special characters)
* Each tag is displayed as a separate label
* Tags are automatically sorted alphabetically on display

### Project Deadline
* Must be in `yyyy-MM-dd` format
* Examples: `2025-12-31`, `2025-06-15`, `2025-01-01`

### Project Name
* Can contain any characters
* Must be unique (case-sensitive)
* Cannot be blank

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous IndiDex home folder.

**Q**: Can I add the same person twice to IndiDex?<br>
**A**: No, IndiDex prevents duplicate contacts. If a duplicate contact with an exact same name already entered is entered again, you will receive an error message.

**Q**: What happens to contacts when I delete a project they're assigned to?<br>
**A**: The contacts remain in IndiDex. Only the project is removed. The project links on the contact cards will be removed.

**Q**: Can I assign contacts to a project after creating it?<br>
**A**: Currently, you need to specify project members when creating the project using the `padd` command. Future versions may support adding members to existing projects.

**Q**: How do I remove a tag from a contact?<br>
**A**: Use the `edit` command and specify only the tags you want to keep. For example, `edit 1 t/keepThisTag` will remove all other tags and keep only "keepThisTag". To remove all tags, use `edit 1 t/`.

**Q**: Are social media profiles mandatory?<br>
**A**: No, all social media profiles (Discord, LinkedIn, Instagram, YouTube) are optional. Only Name, Phone, Email, and Address are required when adding a contact.

**Q**: What's the difference between `tag` and `edit` for adding tags?<br>
**A**: The `tag` command **adds** tags to existing tags (cumulative), while `edit` **replaces** all existing tags with the new ones specified.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
3. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action            | Format, Examples                                                                                                                                                                                           |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Contact**   | `add n/NAME p/PHONE e/EMAIL a/ADDRESS [pr/PRIORITY] [dc/DISCORD] [li/LINKEDIN] [ig/INSTAGRAM] [yt/YOUTUBE] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123 Clementi Rd pr/HIGH dc/jamesho#1234 t/friend` |
| **List**          | `list`                                                                                                                                                                                                    |
| **Edit**          | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pr/PRIORITY] [dc/DISCORD] [li/LINKEDIN] [ig/INSTAGRAM] [yt/YOUTUBE] [t/TAG]…​`<br> e.g., `edit 2 n/James Lee e/jameslee@example.com pr/MEDIUM` |
| **Find**          | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`, `find @gmail.com`                                                                                                                           |
| **Tag**           | `tag INDEX [MORE_INDICES] t/TAG [t/MORE_TAGS]…​`<br> e.g., `tag 1 2 3 t/collaborator t/urgent`                                                                                                          |
| **Delete**        | `delete INDEX [MORE_INDICES]`<br> e.g., `delete 3`, `delete 1 3 5`                                                                                                                                      |
| **Sort**          | `sort (n/\|p/\|e/\|pr/\|a/)[asc/desc] ​`<br> e.g., `sort p/desc`, `sort pr/` |
| **Add Project**   | `padd n/PROJECT_NAME d/DEADLINE pr/PRIORITY [m/MEMBER_INDEX]…​`<br> e.g., `padd n/Web Series d/2025-12-31 pr/HIGH m/1 2 3`                                                                              |
| **Delete Project** | `pdelete n/PROJECT_NAME`<br> e.g., `pdelete Web Series Pilot`                                                                                                                                              |
| **Clear**         | `clear`                                                                                                                                                                                                   |
| **Help**          | `help`                                                                                                                                                                                                    |
| **Exit**          | `exit`                                                                                                                                                                                                    |

--------------------------------------------------------------------------------------------------------------------

## Glossary

* **CLI (Command Line Interface)**: A text-based interface where users type commands to interact with the application
* **GUI (Graphical User Interface)**: A visual interface with windows, buttons, and other graphical elements
* **Index**: The position number of a contact in the displayed list (starts from 1)
* **Parameter**: Information you provide in a command (e.g., name, phone number)
* **Tag**: A label used to categorize contacts (e.g., "friend", "colleague", "urgent")
* **Priority**: A classification of importance (LOW, MEDIUM, or HIGH) for contacts or projects
* **Project**: A collection of information about a collaborative effort, including deadline, priority, and team members
* **JSON**: A file format used to store IndiDex data
* **Indie Content Creator**: A self-employed person who creates digital content like videos, podcasts, music, or animations and shares it online. "Indie" (short for independent) means they work for themselves rather than for a company, and they often collaborate with other freelancers to produce their content. Examples include independent YouTubers, podcasters, Twitch streamers, and digital artists.

--------------------------------------------------------------------------------------------------------------------
