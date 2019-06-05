import os
import pathlib
from pathlib import Path
import re


def has_admin():
    if os.name == 'nt':
        try:
            # only windows users with admin privileges can read the C:\windows\temp
            temp = os.listdir(os.sep.join([os.environ.get('SystemRoot', 'C:\\windows'), 'temp']))
        except:
            return os.environ['USERNAME'],False
        else:
            return os.environ['USERNAME'],True
    else:
        if 'SUDO_USER' in os.environ and os.geteuid() == 0:
            return os.environ['SUDO_USER'],True
        else:
            return os.environ['USERNAME'],False


print('Script Start')

isAdmin = has_admin()
print('Running As Admin - ', isAdmin[1])
if not isAdmin[1]:
    print('Can\'t run without admin privileges')
    exit()

pathlist = Path('src/').glob('**/*')
# Delete the old directory
os.system('echo y | rmdir windows /s')

for path in pathlist:
    path_in_str = str(path)
    if os.path.isfile(path):
        # check if it's a link to a file or folder
        source_link_str = path_in_str
        source_link_str = '..\\base\\' + source_link_str
        # Remove the root folder for the actual link
        print(source_link_str)

        testDest = 'windows\\' + path_in_str

        directory = pathlib.Path(testDest).parent
        print(str(directory))
        prefixDir = (re.sub(r"\\+[^\\]*", r"\\..", str(directory))+'\\..\\')[8:]  # 8 to remove windows/
        # Change all the directory paths into .. so that it will properly move up a folder.

        os.system('mkdir %s' % directory)
        os.system('icacls %s /grant Everyone:(f)' % directory)

        # Now we need to go through each destination directory and understand that's how many ../ we have to add
        if path_in_str.endswith('.java'):
            print('Java file link found')

            command = 'mklink "%s" "%s%s"' % (testDest, prefixDir, source_link_str)
            print('Performing command %s' % command)
            os.system(command)
        else:
            print('Directory link found')
            command = 'mklink /D "%s" "%s%s"' % (testDest, prefixDir, source_link_str)
            print('Performing command %s' % command)
            os.system(command)

print('Script Ends')


