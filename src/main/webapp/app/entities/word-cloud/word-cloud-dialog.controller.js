(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WordCloudDialogController', WordCloudDialogController);

    WordCloudDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WordCloud', 'PersonWordCloud'];

    function WordCloudDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WordCloud, PersonWordCloud) {
        var vm = this;

        vm.wordCloud = entity;
        vm.clear = clear;
        vm.save = save;
        vm.personwordclouds = PersonWordCloud.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.wordCloud.id !== null) {
                WordCloud.update(vm.wordCloud, onSaveSuccess, onSaveError);
            } else {
                WordCloud.save(vm.wordCloud, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:wordCloudUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
