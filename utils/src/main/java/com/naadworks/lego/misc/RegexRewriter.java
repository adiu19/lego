package com.naadworks.lego.misc;// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexRewriter {
    private Pattern pattern;
    private Matcher matcher;

    public RegexRewriter(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public String group(int i) {
        return this.matcher.group(i);
    }

    public abstract String replacement();

    public String rewrite(CharSequence original) {
        if (original == null) {
            return null;
        } else {
            this.matcher = this.pattern.matcher(original);
            StringBuffer result = new StringBuffer(original.length());

            while(this.matcher.find()) {
                this.matcher.appendReplacement(result, "");
                result.append(this.replacement());
            }

            this.matcher.appendTail(result);
            return result.toString();
        }
    }
}
