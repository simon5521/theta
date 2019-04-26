package hu.bme.mit.theta.mm.model;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public class  Reward<R extends RewardCommand> {

    public final String name;
    public final Collection<R> commands;


    protected Reward(String name, Collection<R> commands) {
        this.name = name;
        this.commands = commands;
    }

    public RewardCommand.Type getType(){
        if(commands.isEmpty()){ throw new  UnsupportedOperationException(); }
        return commands.iterator().next().getType();
    }

    public Builder<R> builder(){
        return new Builder();
    }

    public static class Builder<R extends RewardCommand>{
        private String name;
        private Collection<R> commands;
        private boolean built;

        public Builder(){
            commands=new HashSet<>();
            built=false;
        }

        public Reward build(){
            checkNotBuilt();
            return new Reward(name,commands);
        }

        public void addCommand(R command){
            checkNotBuilt();
            commands.add(command);
        }

        public void setName(String name) {
            checkNotBuilt();
            this.name = name;
        }

        private void checkNotBuilt() {
            checkState(!built, "A Markov Chain was already built.");
        }

    }

}
