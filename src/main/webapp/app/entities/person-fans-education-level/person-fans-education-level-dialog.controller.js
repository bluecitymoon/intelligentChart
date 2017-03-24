(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansEducationLevelDialogController', PersonFansEducationLevelDialogController);

    PersonFansEducationLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonFansEducationLevel', 'Person', 'EducationLevel'];

    function PersonFansEducationLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonFansEducationLevel, Person, EducationLevel) {
        var vm = this;

        vm.personFansEducationLevel = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});
        vm.educationlevels = EducationLevel.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personFansEducationLevel.id !== null) {
                PersonFansEducationLevel.update(vm.personFansEducationLevel, onSaveSuccess, onSaveError);
            } else {
                PersonFansEducationLevel.save(vm.personFansEducationLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personFansEducationLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
