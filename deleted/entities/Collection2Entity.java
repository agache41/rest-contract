
/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package modell.quarkus.entities;

import io.github.agache41.rest.contract.dataAccess.PrimaryKey;
import io.github.agache41.rest.contract.update.SelfTransferObject;
import io.github.agache41.rest.contract.update.Update;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Collection2Entity implements PrimaryKey<Long>, SelfTransferObject<Collection2Entity>/*, Comparable<CollectionEntity>*/ {
    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Update
    private String subName;

    @Update(dynamic = false)
    private String subStreet;

    @Update(dynamic = false)
    private Integer subNumber;

    @EqualsAndHashCode.Exclude
    private long subAge;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Collection2Entity that = (Collection2Entity) o;
        return Objects.equals(this.subName, that.subName) && Objects.equals(this.subStreet, that.subStreet) && Objects.equals(this.subNumber, that.subNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.subName, this.subStreet, this.subNumber);
    }

/*    @Override
    public int compareTo(final CollectionEntity o) {
        if (!this.subName.equals(o.getSubName())) {
            return this.subName.compareTo(o.getSubName());
        }
        if (!this.subStreet.equals(o.getSubStreet())) {
            return this.subStreet.compareTo(o.getSubStreet());
        }
        if (!this.subNumber.equals(o.getSubNumber())) {
            return this.subNumber.compareTo(o.getSubNumber());
        }
        return 0;
    }*/
}
