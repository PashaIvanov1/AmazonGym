version = 0.1
[y.deploy.parameters]
stack_name = "sam-gym-app"
resolve_s3 = true
s3_prefix = "sam-gym-app"
region = "eu-central-1"
confirm_changeset = true
capabilities = "CAPABILITY_IAM"
image_repositories = []
