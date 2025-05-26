
git checkout master

git checkout -b local-master-backup

git checkout master

git config pull.rebase true

git pull git@github.com:cloudfoundry-samples/spring-music.git master --force

git rebase --skip
git rebase --skip
git rebase --skip
