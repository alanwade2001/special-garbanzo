package main

import (
	"net/http"
)

func main() {

	// l := log.New(os.Stdout, "product-api", log.LstdFlags)
	// hh := handlers.newHello(l)

	http.ListenAndServe(":9090", nil)
}
