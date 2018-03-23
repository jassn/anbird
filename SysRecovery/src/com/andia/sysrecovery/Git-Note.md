
# Notes on git VCS


## 2.4 Undoing Things


## 3.5 Remote Branches

### Pushing
* git push \<remote> \<branch>
* git push \<remote> \<local_branch>:\<remote_branch>
* Example: git push *origin* \<branch>

### Tracking Branches
* git checkout -b \<local_branch> \<remote>/\<branch>

If you already have a local branch and want to set it to a remote branch you just pulled down...
* git branch -u

To see what tracking branches...
* git fetch --all
* git branch -vv
* git merge

### Pulling

### Deleting Remote Branches

## 3.6 Git Branching - Rebasing


### Others
* git log --stat
* git log -p


## References
https://blog.gogojimmy.net/2012/02/29/git-scenario/

https://blog.wu-boy.com/2013/08/git-rebase-stash-tip/

https://gitbook.tw/chapters/using-git/restore-hard-reset-commit.html

https://www.git-tower.com/learn/git/ebook/cn/command-line/advanced-topics/git-flow



