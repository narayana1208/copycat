/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kuujo.copycat.raft.protocol;

import net.kuujo.copycat.util.internal.Assert;

import java.util.Objects;

/**
 * Protocol vote response.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class VoteResponse extends AbstractResponse {

  /**
   * Returns a new vote response builder.
   *
   * @return A new vote response builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns a vote response builder for an existing response.
   *
   * @param response The response to build.
   * @return The vote response builder.
   */
  public static Builder builder(VoteResponse response) {
    return new Builder(response);
  }

  private long term;
  private boolean voted;

  /**
   * Returns the responding node's current term.
   *
   * @return The responding node's current term.
   */
  public long term() {
    return term;
  }

  /**
   * Returns a boolean indicating whether the vote was granted.
   *
   * @return Indicates whether the vote was granted.
   */
  public boolean voted() {
    return voted;
  }

  @Override
  public int hashCode() {
    return Objects.hash(member, status, term, voted);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof VoteResponse) {
      VoteResponse response = (VoteResponse) object;
      return response.member.equals(member)
        && response.status == status
        && response.term == term
        && response.voted == voted;
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format("%s[term=%d, voted=%b]", getClass().getSimpleName(), term, voted);
  }

  /**
   * Poll response builder.
   */
  public static class Builder extends AbstractResponse.Builder<Builder, VoteResponse> {
    protected Builder() {
      this(new VoteResponse());
    }

    protected Builder(VoteResponse response) {
      super(response);
    }

    /**
     * Sets the response term.
     *
     * @param term The response term.
     * @return The vote response builder.
     */
    public Builder withTerm(long term) {
      response.term = Assert.arg(term, term > 0, "term must be greater than zero");
      return this;
    }

    /**
     * Sets whether the vote was granted.
     *
     * @param voted Whether the vote was granted.
     * @return The vote response builder.
     */
    public Builder withVoted(boolean voted) {
      response.voted = voted;
      return this;
    }

    @Override
    public VoteResponse build() {
      super.build();
      Assert.arg(response.term, response.term > 0, "term must be greater than zero");
      return response;
    }

    @Override
    public int hashCode() {
      return Objects.hash(response);
    }

    @Override
    public boolean equals(Object object) {
      return object instanceof Builder && ((Builder) object).response.equals(response);
    }

    @Override
    public String toString() {
      return String.format("%s[response=%s]", getClass().getCanonicalName(), response);
    }

  }

}
