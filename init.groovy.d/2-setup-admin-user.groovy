import hudson.security.*
import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.install.*
import jenkins.model.*
import jenkins.security.s2m.AdminWhitelistRule
import jenkins.security.*

import org.jenkinsci.plugins.authorizeproject.*
import org.jenkinsci.plugins.authorizeproject.strategy.*

def env = System.getenv()

def jenkins = Jenkins.getInstance()

def allowsSignup = false
def hudsonRealm = new HudsonPrivateSecurityRealm(allowsSignup)

def adminUser = hudsonRealm.createAccount("admin", "test")
def customerUser = hudsonRealm.createAccount("venkat", "test")
jenkins.setSecurityRealm(hudsonRealm)

def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, "admin")

strategy.add(Jenkins.READ, "venkat")
strategy.add(hudson.model.View.READ, "venkat")

strategy.add(hudson.model.Item.BUILD, "venkat")
strategy.add(hudson.model.Item.READ, "venkat")
strategy.add(hudson.model.Item.WIPEOUT, "venkat")
strategy.add(hudson.model.Item.WORKSPACE, "venkat")

strategy.add(hudson.model.Run.UPDATE, "venkat")

jenkins.setAuthorizationStrategy(strategy)

jenkins.save()
