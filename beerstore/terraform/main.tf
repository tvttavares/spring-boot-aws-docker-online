provider "aws" {
	version = "~> 3.3.0"
	shared_credentials_file = "~/.aws/credentials"
	profile = "terraform"
}