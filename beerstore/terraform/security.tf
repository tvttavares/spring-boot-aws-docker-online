resource "aws_security_group" "allow_ssh" {
  vpc_id = "${aws_vpc.main.id}"
  name   = "hibicode_allow_ssh"

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    //cidr_blocks = ["${var.my_public_ip}"]
    cidr_blocks = ["189.59.58.200/32"]
  }
}

resource "aws_security_group" "database" {
  vpc_id = "${aws_vpc.main.id}"
  name   = "hibicode_database"

  ingress {
    from_port = 5432
    to_port   = 5432
    protocol  = "tcp"
    self      = true
  }
}
