terraform {
    backend "s3" {
        bucket = "hibicode-terraform-state-test"
        key = "beerstore-curso-online"
        region = "us-east-1"
        profile = "terraform"
    } 
}

