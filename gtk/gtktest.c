
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


void end_program(GtkWidget *wid, gpointer ptr)
{
	gtk_main_quit();
}


int main (int argc, char *argv[])
{
	gtk_init(&argc, &argv);

	GtkWidget *win = gtk_window_new(GTK_WINDOW_TOPLEVEL);
	GtkWidget *btn = gtk_button_new_with_label("Close window");

	g_signal_connect (btn, "clicked", G_CALLBACK(end_program), NULL);

	gtk_container_add(GTK_CONTAINER (win), btn);

	gtk_widget_show_all(win);

	// start the main loop
	gtk_main();

	return 0;
}
