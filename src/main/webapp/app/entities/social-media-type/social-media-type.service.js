(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('SocialMediaType', SocialMediaType);

    SocialMediaType.$inject = ['$resource'];

    function SocialMediaType ($resource) {
        var resourceUrl =  'api/social-media-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
