cl.exe /Fovisible.obj /Tcvisible.c /c
cl.exe /Fopal.obj /Tcpal.c /c
cl.exe /Folinked_list.obj /Tclinked_list.c /c
cl.exe /Foex4.obj /Tcex4.c /c
cl.exe /Feex4.exe ex4.obj linked_list.obj pal.obj visible.obj /link 
