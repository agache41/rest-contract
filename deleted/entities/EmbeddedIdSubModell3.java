
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.agache41.rest.contract.dataAccess.PrimaryKey;
import io.github.agache41.rest.contract.update.SelfTransferObject;
import io.github.agache41.rest.contract.update.Update;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Update
@Entity
public class EmbeddedIdSubModell3 extends BaseEntity implements PrimaryKey<EmbeddedKeys>, SelfTransferObject<EmbeddedIdSubModell3> {

    private static final long serialVersionUID = 4145235006835414021L;

    @EmbeddedId
    private EmbeddedKeys id;

    @JsonIgnore
    @Update.excluded
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "key1", referencedColumnName = "key1", insertable = false, updatable = false),
            @JoinColumn(name = "key2", referencedColumnName = "key2", insertable = false, updatable = false),
            @JoinColumn(name = "key3", referencedColumnName = "key3", insertable = false, updatable = false),
    })
    private EmbeddedIdModell parent;


}