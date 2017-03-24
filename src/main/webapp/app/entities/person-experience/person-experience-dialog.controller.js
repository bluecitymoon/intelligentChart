(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonExperienceDialogController', PersonExperienceDialogController);

    PersonExperienceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonExperience', 'Person'];

    function PersonExperienceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonExperience, Person) {
        var vm = this;

        vm.personExperience = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personExperience.id !== null) {
                PersonExperience.update(vm.personExperience, onSaveSuccess, onSaveError);
            } else {
                PersonExperience.save(vm.personExperience, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personExperienceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
