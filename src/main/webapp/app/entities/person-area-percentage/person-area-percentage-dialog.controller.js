(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonAreaPercentageDialogController', PersonAreaPercentageDialogController);

    PersonAreaPercentageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonAreaPercentage', 'Person', 'MediaType', 'AreaType'];

    function PersonAreaPercentageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonAreaPercentage, Person, MediaType, AreaType) {
        var vm = this;

        vm.personAreaPercentage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});
        vm.mediatypes = MediaType.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});
        vm.areatypes = AreaType.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            console.debug(vm.personAreaPercentage.person);
            vm.isSaving = true;
            if (vm.personAreaPercentage.id !== null) {
                PersonAreaPercentage.update(vm.personAreaPercentage, onSaveSuccess, onSaveError);
            } else {
                PersonAreaPercentage.save(vm.personAreaPercentage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personAreaPercentageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
