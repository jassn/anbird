
/*

Chapter 14
Your first GTK program

Start coding in C with the GTK library and create your first simple GUI application

SOURCE FILE: gtktest.c


* Building
sudo apt-get install libgtk2.0-dev

pkg-config --cflags --libs gtk+-2.0


gcc -o gtktest gtktest.c `pkg-config --cflags --libs gtk+-2.0`

*/


#include <gtk/gtk.h>

int main (int argc, char *argv[])
{
	gtk_init(&argc, &argv);
	GtkWidget *win = gtk_window_new(GTK_WINDOW_TOPLEVEL);
	gtk_widget_show(win);
	gtk_main();

	return 0;
}
