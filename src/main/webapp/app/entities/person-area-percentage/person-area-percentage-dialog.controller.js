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
        vm.people = Person.query();
        vm.mediatypes = MediaType.query();
        vm.areatypes = AreaType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
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
